package cn.jihangyu.glowworm.message.service;

import cn.jihangyu.glowworm.message.entity.Message;
import cn.jihangyu.glowworm.message.entity.MessageElement;

import java.util.List;

public interface MessageService {
    String addMessage(Message message);

    List<MessageElement> getRequestMessage(String to_uid);

    Message updateMessage(Message message);

    List<MessageElement> getReplyMessage(String from_uid);

    Message getMessageById(Integer id);
}
