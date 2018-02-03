package cn.jihangyu.glowworm.user.service;

import cn.jihangyu.glowworm.activity.entity.Activity;
import cn.jihangyu.glowworm.user.entity.User;

import java.util.List;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.user.service
 * @Description: TODO
 * @date 2018/2/2 9:06
 */
public interface UserService {
    /**
     * 添加用户
     * @param user
     * @return
     * @throws Exception
     */
    User addUser(User user) throws Exception;

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    User findUserById(Integer id);

    /**
     * 修改用户
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据id删除用户
     * @param id
     */
    void deleteUserById(Integer id);

    /**
     * 根据用户id和活动状态查找他参加的的活动{1：未进行，2：正在进行，3：已结束，0所有}
     * @param id 用户id
     * @param state 活动状态
     * @return 活动集合
     */
    List<Activity> findActiviysByUserId(Integer id,Integer state);
}
