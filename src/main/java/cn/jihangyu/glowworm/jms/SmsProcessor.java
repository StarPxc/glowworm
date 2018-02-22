package cn.jihangyu.glowworm.jms;

import cn.jihangyu.glowworm.sms.SmsSender;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;


@Component(value = "smsProcessor")
public class SmsProcessor {
    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    @Autowired
    @Qualifier("aliSmsSender")
    private SmsSender smsSender;


    public  void sendSmsToQueue(Destination destination, final String message){
        System.out.println("进入smsProcessor");
        jmsTemplate.convertAndSend(destination, message);
    }

    @JmsListener(destination="sms.queue")
    public void doSendSmsMessage(String text) throws ClientException {
        JSONObject jsonObject = JSON.parseObject(text);
        System.out.println("已被监听到");
        smsSender.sendSms(jsonObject.getString("phone"),jsonObject.getString("signName"),jsonObject.getString("templateCode"),jsonObject.getString("to_username"),jsonObject.getString("bookname"));
    }
}
