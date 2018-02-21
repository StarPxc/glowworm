package cn.jihangyu.glowworm.message.controller;

import cn.jihangyu.glowworm.common.base.BaseController;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.message.entity.Message;
import cn.jihangyu.glowworm.message.service.MessageService;
import cn.jihangyu.glowworm.user.entity.User;
import cn.jihangyu.glowworm.user.entity.UserElement;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController extends BaseController {

    @Autowired
    @Qualifier("messageServiceImpl")
    @Lazy
    private MessageService messageService;


    @ApiOperation(value="发送请求消息", notes="发送请求消息")
    @ApiImplicitParam(name = "message", value = "消息详细实体message", required = true, dataType = "Message")
    @RequestMapping(value = "/sendMyRequest",method = RequestMethod.POST)
    public ApiResult sendMyRequest(@RequestBody Message message){
        //此时message的pass值必须为0（未回复）
        UserElement ue=getCurrentUser();
        if(message.getFromUid().equals(ue.getUserId())){
            //发送请求消息，相当于向数据库插入一条message记录
            //返回from_uid+to_uid+bid
            String result=messageService.addMessage(message);
            return ResultUtil.success(result);
        }else{
            //假如message的from_uid和当前用户id不匹配，则抛出没有权限
            return ResultUtil.error(ResultEnum.NO_AUTHORITY.getCode(),ResultEnum.NO_AUTHORITY.getMsg());
        }
    }


    @ApiOperation(value="获取和用户本人相关的请求消息", notes="获取和用户本人相关的请求消息")
    @RequestMapping(value = "/getMyRequest/{to_uid}",method = RequestMethod.GET)
    public ApiResult getMyRequest(@PathVariable String to_uid){
        //可以获取所有to_uid为当前用户的message
        UserElement ue=getCurrentUser();
        if(to_uid.equals(ue.getUserId())){
            //返回查询到的message实体列表
            List<Message> messages=messageService.getRequestMessage(to_uid);
            return ResultUtil.success(messages);
        }else{
            //假如message的to_uid和当前用户id不匹配，则抛出没有权限
            return ResultUtil.error(ResultEnum.NO_AUTHORITY.getCode(),ResultEnum.NO_AUTHORITY.getMsg());
        }
    }

    @ApiOperation(value="发送回复消息", notes="发送回复消息")
    @ApiImplicitParam(name = "message", value = "消息详细实体message", required = true, dataType = "Message")
    @RequestMapping(value = "/sendMyReply",method = RequestMethod.POST)
    public ApiResult sendMyReply(@RequestBody Message message){
        //发送消息主要是更新这条message数据的pass值，将0（未回复）改为1（同意）或2（拒绝）
        UserElement ue=getCurrentUser();
        if(message.getToUid().equals(ue.getUserId())){
            //返回message更新后的实体
            Message result=messageService.updateMessage(message);
            return ResultUtil.success(result);
        }else{
            //回复消息的发送者id即为message记录的to_uid,假如message的to_uid和当前用户id不匹配，则抛出没有权限
            return ResultUtil.error(ResultEnum.NO_AUTHORITY.getCode(),ResultEnum.NO_AUTHORITY.getMsg());
        }

    }


    @ApiOperation(value="获取和用户本人相关的回复消息", notes="获取和用户本人相关的回复消息")
    @RequestMapping(value = "/getMyReply/{from_uid}",method = RequestMethod.GET)
    public ApiResult getMyReply(@PathVariable String from_uid){
        //获取回复消息，前提是message的记录的pass字段非0
        //因为0表示还未处理，这时相当于还未得到他人回复，所以不应该被查询到
        //pass字段为1（同意）或2（拒绝），这时表示已经得到回复，所以应该被查询到
        UserElement ue=getCurrentUser();
        if(from_uid.equals(ue.getUserId())){
            //返回查询到的message实体列表
            List<Message> messages=messageService.getReplyMessage(from_uid);
            return ResultUtil.success(messages);
        }else{
            //假如message的from_uid和当前用户id不匹配，则抛出没有权限
            return ResultUtil.error(ResultEnum.NO_AUTHORITY.getCode(),ResultEnum.NO_AUTHORITY.getMsg());
        }
    }

    @ApiOperation(value="根据消息id获取消息记录", notes="根据消息id获取消息记录")
    @RequestMapping(value = "/getMessageById/{id}",method = RequestMethod.GET)
    public ApiResult getMessageById(@PathVariable Integer id){
        Message message=messageService.getMessageById(id);
        return ResultUtil.success(message);
    }


}
