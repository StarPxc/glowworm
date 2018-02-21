package cn.jihangyu.glowworm.message.service;

import cn.jihangyu.glowworm.message.entity.Message;

import java.util.List;

public interface MessageService {
    String addMessage(Message message);

    List<Message> getRequestMessage(String to_uid);

    Message updateMessage(Message message);

    List<Message> getReplyMessage(String from_uid);

    Message getMessageById(Integer id);
}
