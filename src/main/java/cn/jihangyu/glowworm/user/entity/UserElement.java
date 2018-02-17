package cn.jihangyu.glowworm.user.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.user.entity
 * @Description: TODO
 * @date 2018/2/7 12:03
 */
@Data
public class UserElement {

    private String userId;
    private String token;


    /**
     * 转 map
     *
     * @return
     */
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", this.userId + "");
        map.put("token", token);

        return map;
    }

    /**
     * map转对象
     *
     * @param map
     * @return
     */
    public static UserElement fromMap(Map<String, String> map) {
        UserElement ue = new UserElement();
        ue.setToken(map.get("token"));
        ue.setUserId(map.get("userId"));
        return ue;
    }
}