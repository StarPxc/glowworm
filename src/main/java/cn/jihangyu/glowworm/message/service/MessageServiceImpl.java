package cn.jihangyu.glowworm.message.service;

import cn.jihangyu.glowworm.book.dao.BookMapper;
import cn.jihangyu.glowworm.book.entity.Book;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.jms.SmsProcessor;
import cn.jihangyu.glowworm.message.dao.MessageMapper;
import cn.jihangyu.glowworm.message.entity.Message;
import cn.jihangyu.glowworm.user.dao.UserMapper;
import cn.jihangyu.glowworm.user.entity.User;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private static final String SMS_QUEUE ="sms.queue" ;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private SmsProcessor smsProcessor;

    @Override
    public String addMessage(Message message) {
        String result="请求者id："+message.getFromUid()+"接收者id："+message.getToUid()+"书id:"+message.getBid();
        try{
            //需要做重复检测，假如一条记录的from_uid,to_uid,bid相同，即视为同一条记录，不需要再插入。
            Message temp=messageMapper.selectByBid(message.getBid());
            if(temp==null){
                //如果查询不到关于这本书的消息记录，可插入记录
                messageMapper.insertSelective(message);
                //获取收信人信息
                User to_user=userMapper.selectByPrimaryKey(message.getToUid());
                String phone=to_user.getUPhone();
                String to_username=to_user.getUNickname();
                //获取书籍信息
                Book selectBook=bookMapper.selectByPrimaryKey(message.getBid());
                String bookname=selectBook.getbName();
                //可以发送短信,发送消息到队列
                Destination destination=new ActiveMQQueue(SMS_QUEUE);
                Map<String,String> smsParam = new HashMap<>();
                smsParam.put("phone",phone);
                smsParam.put("signName","萤火虫共享图书");
                smsParam.put("templateCode","SMS_125028753");
                smsParam.put("to_username",to_username);
                smsParam.put("bookname",bookname);
                String sendMessage = JSON.toJSONString(smsParam);
                smsProcessor.sendSmsToQueue(destination,sendMessage);
                return result;
            }else{
                //如果查询到了关于这本书的消息记录，但可能是几个不同的用户发出的请求消息，此时需要判断
                if(temp.getFromUid().equals(message.getFromUid())&&temp.getToUid().equals(message.getToUid())){
                        throw new GlowwormExecption(ResultEnum.MESSAGE_EXIST);
                }else{
                    messageMapper.insertSelective(message);
                    //获取收信人信息
                    User to_user=userMapper.selectByPrimaryKey(message.getToUid());
                    String phone=to_user.getUPhone();
                    String to_username=to_user.getUNickname();
                    //获取书籍信息
                    Book selectBook=bookMapper.selectByPrimaryKey(message.getBid());
                    String bookname=selectBook.getbName();
                    //可以发送短信,发送消息到队列
                    Destination destination=new ActiveMQQueue(SMS_QUEUE);
                    Map<String,String> smsParam = new HashMap<>();
                    smsParam.put("phone",phone);
                    smsParam.put("signName","萤火虫共享图书");
                    smsParam.put("templateCode","SMS_125028753");
                    smsParam.put("to_username",to_username);
                    smsParam.put("bookname",bookname);
                    String sendMessage = JSON.toJSONString(smsParam);
                    smsProcessor.sendSmsToQueue(destination,sendMessage);
                    return result;
                }
            }

        }catch (Exception e){
            System.out.println(e);
            throw new GlowwormExecption(ResultEnum.OBJECT_ADD_ERROR);

        }
    }

    @Override
    public List<Message> getRequestMessage(String to_uid) {
        try{
            List<Message> messages=messageMapper.selectByToUid(to_uid);
            return messages;
        }catch (Exception e){
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        }
    }

    @Override
    public Message updateMessage(Message message) {
        try{
            int code=messageMapper.updateByPrimaryKeySelective(message);
            if(code==1){
                Message message_after_update=messageMapper.selectByPrimaryKey(message.getId());
                //获取收信人信息
                User to_user=userMapper.selectByPrimaryKey(message.getFromUid());
                String phone=to_user.getUPhone();
                String to_username=to_user.getUNickname();
                //获取书籍信息
                Book selectBook=bookMapper.selectByPrimaryKey(message.getBid());
                String bookname=selectBook.getbName();
                //可以发送短信,发送消息到队列
                Destination destination=new ActiveMQQueue(SMS_QUEUE);
                Map<String,String> smsParam = new HashMap<>();
                smsParam.put("phone",phone);
                smsParam.put("signName","萤火虫共享图书");
                smsParam.put("templateCode","SMS_125018858");
                smsParam.put("to_username",to_username);
                smsParam.put("bookname",bookname);
                String sendMessage = JSON.toJSONString(smsParam);
                smsProcessor.sendSmsToQueue(destination,sendMessage);
                return message_after_update;
            }else{
                throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
            }
        }catch (Exception e){
            throw new GlowwormExecption(ResultEnum.OBJECT_UPDATE_ERROR);
        }
    }

    @Override
    public List<Message> getReplyMessage(String from_uid) {
        try{
            List<Message> messages=messageMapper.selectByFromUidAndisReplyed(from_uid);
            return messages;
        }catch (Exception e){
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        }
    }

    @Override
    public Message getMessageById(Integer id) {
        Message message=messageMapper.selectByPrimaryKey(id);
        return message;
    }
}
