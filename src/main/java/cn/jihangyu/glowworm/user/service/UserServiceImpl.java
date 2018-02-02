package cn.jihangyu.glowworm.user.service;

import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.utils.MyUtil;
import cn.jihangyu.glowworm.user.dao.UserMapper;
import cn.jihangyu.glowworm.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.user.service
 * @Description: TODO
 * @date 2018/2/2 9:06
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;

    @Override
    public int addUser(User user) throws Exception {
        if(user==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if(MyUtil.isAllFieldNull(user)){
            throw new GlowwormExecption(ResultEnum.OBJECT_ALL_Field_NULL);
        }
        try {
            mapper.insertSelective(user);
        }catch (Exception e){
            log.error("【数据库插入数据异常】",e);
            throw new GlowwormExecption(ResultEnum.DB_INSERT_ERROR);
        }
        return 0;
    }
}
