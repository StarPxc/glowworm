package cn.jihangyu.glowworm.userRemarkActivity.service;

import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.utils.MyUtil;
import cn.jihangyu.glowworm.userRemarkActivity.dao.UserRemarkActivityMapper;
import cn.jihangyu.glowworm.userRemarkActivity.entity.UserRemarkActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.userRemarkActivity.service
 * @Description: TODO
 * @date 2018/2/5 16:38
 */
@Service
@Slf4j
public class UserRemarkActivityServiceImpl implements UserRemarkActivityService{
    @Autowired
    private UserRemarkActivityMapper userRemarkActivityMapper;
    private static ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private static Lock rlock = lock.readLock();
    private static Lock wlock = lock.writeLock();
    @Override
    public UserRemarkActivity addComment(UserRemarkActivity userRemarkActivity) throws Exception {
        if(userRemarkActivity==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if(MyUtil.isAllFieldNull(userRemarkActivity)){
            throw new GlowwormExecption(ResultEnum.OBJECT_ALL_Field_NULL);
        }
        wlock.lock();
        try {
            userRemarkActivityMapper.insertSelective(userRemarkActivity);
        }catch (Exception e){
            log.error("【添加对象失败】",e);
            throw new GlowwormExecption(ResultEnum.OBJECT_ADD_ERROR);
        }finally {
            wlock.unlock();
        }
        return userRemarkActivity;
    }

    @Override
    public void deleteUserRemarkActivityById(Integer id) {
        if(id==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        wlock.lock();
        try {
            int code= userRemarkActivityMapper.deleteByPrimaryKey(id);
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

    @Override
    public List<UserRemarkActivity> selectUserRemarkActivitysByUIdAndAId(Integer uId, Integer aId) {
        List<UserRemarkActivity>   userRemarkActivitys;
        if(uId==null||aId==null) {
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if(uId==0&&aId!=0){
            rlock.lock();
            try {
                userRemarkActivitys=userRemarkActivityMapper.selectUserRemarkActivitysByAId(aId);
            }finally {
                rlock.unlock();
            }
        }else if(uId!=0&&aId==0){
            rlock.lock();
            try {
                userRemarkActivitys=userRemarkActivityMapper.selectUserRemarkActivitysByUId(uId);
            }finally {
                rlock.unlock();
            }
        }else if(uId==0&&aId==0){
            rlock.lock();
            try {
                userRemarkActivitys=userRemarkActivityMapper.selectUserRemarkActivitys();
            }finally {
                rlock.unlock();
            }
        }else {
            rlock.lock();
            try {
                userRemarkActivitys=userRemarkActivityMapper.selectUserRemarkActivityByUIdAndAId(uId,aId);
            }finally {
                rlock.unlock();
            }
        }

        return userRemarkActivitys;
    }
}
