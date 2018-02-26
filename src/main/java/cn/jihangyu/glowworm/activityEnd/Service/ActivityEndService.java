package cn.jihangyu.glowworm.activityEnd.Service;

import cn.jihangyu.glowworm.activityEnd.entity.ActivityEnd;

import java.util.List;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.activityEnd.Service
 * @Description: TODO
 * @date 2018/2/24 10:50
 */
public interface ActivityEndService {
    /**
     * 结束活动
     * @param activityEnd
     */
    void stopActivityEnd(ActivityEnd activityEnd);

    /**
     * 查看结束的活动
     * @param id
     * @return
     */
    ActivityEnd findActivityEnd(Integer id);

    /**
     * 查看所有结束的活动
     * @return
     */
    List<ActivityEnd> findAllActivityEnd();
}
