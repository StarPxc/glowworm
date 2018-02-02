package cn.jihangyu.glowworm.user.service;

import cn.jihangyu.glowworm.user.entity.User; /**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.user.service
 * @Description: TODO
 * @date 2018/2/2 9:06
 */
public interface UserService {
    int addUser(User user) throws Exception;
}
