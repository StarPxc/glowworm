package cn.jihangyu.glowworm.message.dao;

import cn.jihangyu.glowworm.message.entity.Message;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    List<Message> selectByToUid(String to_uid);

    List<Message> selectByFromUidAndisReplyed(String from_uid);

    Message selectByFromUidAndBid(String fromUid,Integer bid);
}