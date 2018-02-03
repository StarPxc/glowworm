package cn.jihangyu.glowworm.usAc.service;

import cn.jihangyu.glowworm.usAc.entity.UsAc;

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
}
