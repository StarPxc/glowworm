package cn.jihangyu.glowworm.user.service;


import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.utils.MyUtil;
import cn.jihangyu.glowworm.user.dao.UserMapper;
import cn.jihangyu.glowworm.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.user.service
 * @Description: TODO 用户业务
 * @date 2018/2/2 9:06
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    @Lazy
    private UserMapper userMapper;

    @Override
    public User addUser(User user) throws Exception {
        if(user==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if(MyUtil.isAllFieldNull(user)){
            throw new GlowwormExecption(ResultEnum.OBJECT_ALL_Field_NULL);
        }
        try {
            userMapper.insertSelective(user);
        }catch (Exception e){
            log.error("【添加对象失败】",e);
            throw new GlowwormExecption(ResultEnum.OBJECT_ADD_ERROR);
        }
        return user;
    }

    @Override
    public User findUserById(Integer id) {
        User user= userMapper.selectByPrimaryKey(id);
        if(user==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        try {
            int code= userMapper.updateByPrimaryKeySelective(user);
            if(code!=1){
                throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
            }
        }catch (GlowwormExecption e){
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        }catch (Exception e){
            log.error("【修改用户失败】",e);
            throw new GlowwormExecption(ResultEnum.OBJECT_UPDATE_ERROR);

        }

    }

    @Override
    public void deleteUserById(Integer id) {
        try {
            int code= userMapper.deleteByPrimaryKey(id);
            if(code!=1){
                throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
            }
        }catch (GlowwormExecption e){
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        }
        catch (Exception e){
            log.error("【删除对象失败】",e);
            throw new GlowwormExecption(ResultEnum.OBJECT_DELETE_ERROR);

        }
    }


}
