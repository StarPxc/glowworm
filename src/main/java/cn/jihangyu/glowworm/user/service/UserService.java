package cn.jihangyu.glowworm.user.service;

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


}
