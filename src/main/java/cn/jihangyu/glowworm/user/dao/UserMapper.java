package cn.jihangyu.glowworm.user.dao;

import cn.jihangyu.glowworm.user.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer uId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    List<User> selectUsersByActivityId(Integer id);
}