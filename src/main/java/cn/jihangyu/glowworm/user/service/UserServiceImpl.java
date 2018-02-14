package cn.jihangyu.glowworm.user.service;


import cn.jihangyu.glowworm.cache.CommonCacheUtil;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.utils.MD5Util;
import cn.jihangyu.glowworm.common.utils.MyUtil;
import cn.jihangyu.glowworm.user.dao.UserMapper;
import cn.jihangyu.glowworm.user.entity.User;
import cn.jihangyu.glowworm.user.entity.UserElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
    @Autowired
    private CommonCacheUtil cacheUtil;
    private static ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private static Lock rlock = lock.readLock();
    private static Lock wlock = lock.writeLock();
    @Override
    public String addUser(User user) throws Exception {
        String token = null;
        if(user==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if(MyUtil.isAllFieldNull(user)){
            throw new GlowwormExecption(ResultEnum.OBJECT_ALL_Field_NULL);
        }

        wlock.lock();
        try {
            userMapper.insertSelective(user);
            token = generateToken(user);
        }catch (Exception e){
            log.error("【添加对象失败】",e);
            throw new GlowwormExecption(ResultEnum.OBJECT_ADD_ERROR);
        }finally {
            wlock.unlock();
        }
        /*
         * token相当于session而session是有获得用户信息的功能，进MD5加密后的token不具备
         * 所以我们应该把token作为key，把用户信息作为值存入redis，在通过用户的id得到他的token
         */
        UserElement ue = new UserElement();
        ue.setUserId(user.getUId());
        ue.setToken(token);
        ue.setRole(user.getURole()==null?"user":user.getURole());
        cacheUtil.putTokenWhenLogin(ue);
        return token;
    }

    /**
     * 生成token
     *
     * @param user
     * @return
     */
    private String generateToken(User user) {
        String source = user.getUId() +":" + System.currentTimeMillis();
        return MD5Util.getMD5(source);
    }

    @Override
    public User findUserById(Integer id) {
        User user;
        rlock.lock();
        try {
            user= userMapper.selectByPrimaryKey(id);
        }finally {
            rlock.unlock();
        }
        if(user==null){
            throw new GlowwormExecption(ResultEnum.NO_USER);
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        wlock.lock();
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
        }finally {
            wlock.unlock();
        }

    }

    @Override
    public void deleteUserById(Integer id) {
        if(id==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        wlock.lock();
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

        }finally {
            wlock.unlock();
        }
    }


}
