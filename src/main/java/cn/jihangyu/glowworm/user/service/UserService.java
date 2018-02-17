package cn.jihangyu.glowworm.user.service;

import cn.jihangyu.glowworm.user.entity.User;

import java.io.IOException;
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
     * 根据id查找用户
     * @param id
     * @return
     */
   User findUserById(String id);

    /**
     * 修改用户
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据id删除用户
     * @param openid
     */
    //void deleteUserById(String openid);

    /**
     * 登录
     * @param code 微信code
     * @return token
     */
    String login(String code) throws IOException;
}
