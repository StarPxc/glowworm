package cn.jihangyu.glowworm.activity.service;

import cn.jihangyu.glowworm.activity.entity.Activity;


import java.util.List;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.activity.service
 * @Description: TODO
 * @date 2018/2/3 17:06
 */
public interface ActivityService {
    /**
     * 根据id获取活动
     * @param id
     * @return
     */
    Activity getActivityByActivityId(Integer id) throws Exception;

    /**
     * 根据活动对象创建活动
     * @param activity
     */
    Activity addActivity(Activity activity) throws Exception;

    /**
     * 根据活动对象修改活动
     * @param activity
     */
    void updateActivity(Activity activity);

    /**
     * 根据id删除活动
     * @param id
     */
    void deleteActivityById(Integer id);

    /**
     * 查找不同类型的活动
     * @param state
     * @return
     */
    List<Activity> findActiviysByState(Integer state);


}
