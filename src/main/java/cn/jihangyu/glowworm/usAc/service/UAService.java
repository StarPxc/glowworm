package cn.jihangyu.glowworm.usAc.service;

import cn.jihangyu.glowworm.activity.entity.Activity;
import cn.jihangyu.glowworm.usAc.entity.UsAc;
import cn.jihangyu.glowworm.user.entity.User;

import java.util.List;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.usAc.service
 * @Description: TODO
 * @date 2018/2/3 19:28
 */
public interface UAService {
    /**
     * 用户参加活动
     * @param usAc
     * @return
     */
    UsAc addUsAc(UsAc usAc) throws Exception;

    /**
     * 根据id删除用户和活动的关系
     * @param id
     */
    void deleteUAById(Integer id);
    /**
     *根据活动id获取报名这个活动的用户
     * @param id 活动id
     * @return
     */
    List<User> findUsersByActivityId(Integer id);
    /**
     * 根据用户id和活动状态查找他参加的的活动{1：未进行，2：正在进行，3：已结束，0所有}
     * @param uId 用户id
     * @param state 活动状态
     * @return 活动集合
     */
    List<Activity> findActiviysByUserId(String uId, Integer state);
}
