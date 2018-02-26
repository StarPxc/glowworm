package cn.jihangyu.glowworm.activityEnd.dao;

import cn.jihangyu.glowworm.activityEnd.entity.ActivityEnd;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ActivityEndMapper {
    int deleteByPrimaryKey(Integer endId);

    int insert(ActivityEnd record);

    int insertSelective(ActivityEnd record);

    ActivityEnd selectByPrimaryKey(Integer endId);

    int updateByPrimaryKeySelective(ActivityEnd record);

    int updateByPrimaryKey(ActivityEnd record);

    List<ActivityEnd> selectAll();
}