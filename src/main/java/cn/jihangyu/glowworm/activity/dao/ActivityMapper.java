package cn.jihangyu.glowworm.activity.dao;

import cn.jihangyu.glowworm.activity.entity.Activity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ActivityMapper {
    int deleteByPrimaryKey(Integer aId);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer aId);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);
    List<Activity> selectActiviysByUserId(String id, Integer state);

    List<Activity> selectAllActiviysByUserId(String id);

    List<Activity> selectAllActiviys();

    List<Activity> selectActiviysByState(Integer state);
}