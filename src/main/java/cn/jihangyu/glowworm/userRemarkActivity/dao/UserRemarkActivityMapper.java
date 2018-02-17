package cn.jihangyu.glowworm.userRemarkActivity.dao;

import cn.jihangyu.glowworm.userRemarkActivity.entity.UserRemarkActivity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRemarkActivityMapper {
    int deleteByPrimaryKey(Integer uraId);

    int insert(UserRemarkActivity record);

    int insertSelective(UserRemarkActivity record);

    UserRemarkActivity selectByPrimaryKey(Integer uraId);

    int updateByPrimaryKeySelective(UserRemarkActivity record);

    int updateByPrimaryKey(UserRemarkActivity record);
    List<UserRemarkActivity> selectUserRemarkActivityByUIdAndAId(String uId, Integer aId);

    List<UserRemarkActivity>  selectUserRemarkActivitysByUId(String uId);

    List<UserRemarkActivity>  selectUserRemarkActivitysByAId(Integer aId);

    List<UserRemarkActivity>  selectUserRemarkActivitys();
}