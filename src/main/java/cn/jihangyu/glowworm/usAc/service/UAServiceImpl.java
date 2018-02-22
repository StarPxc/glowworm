package cn.jihangyu.glowworm.usAc.service;

import cn.jihangyu.glowworm.activity.dao.ActivityMapper;
import cn.jihangyu.glowworm.activity.entity.Activity;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.utils.MyUtil;
import cn.jihangyu.glowworm.usAc.dao.UsAcMapper;
import cn.jihangyu.glowworm.usAc.entity.UsAc;
import cn.jihangyu.glowworm.user.dao.UserMapper;
import cn.jihangyu.glowworm.user.entity.User;
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
 * @Package cn.jihangyu.glowworm.usAc.service
 * @Description: TODO
 * @date 2018/2/3 19:29
 */
@Service
@Slf4j
public class UAServiceImpl implements UAService {
    @Autowired
    private UsAcMapper usAcMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ActivityMapper activityMapper;
    private static ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private static Lock rlock = lock.readLock();
    private static Lock wlock = lock.writeLock();
    @Override
    public UsAc addUsAc(UsAc usAc,String phone) throws Exception {
        if(phone==null||phone.equals("")){
            throw new GlowwormExecption(ResultEnum.PHONE_NULL_ERROR);
        }
        if (usAc == null) {
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if (MyUtil.isAllFieldNull(usAc)) {
            throw new GlowwormExecption(ResultEnum.OBJECT_ALL_Field_NULL);
        }
        if (userMapper.selectByPrimaryKey(usAc.getUId()) == null) {
            throw new GlowwormExecption(ResultEnum.NO_USER);
        }
        if (activityMapper.selectByPrimaryKey(usAc.getAId()) == null) {
            throw new GlowwormExecption(ResultEnum.NO_ACTIVITY);
        }
        User user=new User();
        user.setUId(usAc.getUId());
        user.setUPhone(phone);
        userMapper.updateByPrimaryKey(user);
        UsAc usAc1=usAcMapper.selectUsAcByUidAndAid(usAc.getUId(),usAc.getAId());
        if(usAc1!=null){
            throw new GlowwormExecption(ResultEnum.HAS_JOIN);
        }
        wlock.lock();
        try {
            usAcMapper.insertSelective(usAc);
        } catch (Exception e) {
            log.error("【参加活动失败】", e);
            throw new GlowwormExecption(ResultEnum.OBJECT_ADD_ERROR);
        }finally {
            wlock.unlock();
        }

        return usAc;
    }

    @Override
    public void deleteUAById(Integer id) {
        if(id==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        wlock.lock();
        try {
            int code = usAcMapper.deleteByPrimaryKey(id);
            if (code != 1) {
                throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
            }
        } catch (GlowwormExecption e) {
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        } catch (Exception e) {
            log.error("【删除对象失败】", e);
            throw new GlowwormExecption(ResultEnum.OBJECT_DELETE_ERROR);
        }finally {
            wlock.unlock();
        }
    }

    @Override
    public List<User> findUsersByActivityId(Integer id) {
        if (id == null) {
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        rlock.lock();
        List<User> users;
        try{
            users = userMapper.selectUsersByActivityId(id);
        }finally {
            rlock.unlock();
        }
        return users;
    }
    @Override
    public List<Activity> findActiviysByUserId(String id, Integer state) {
        List<Activity> activities;
        if(id==null||state==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        rlock.lock();
        try {
            if(state==0){
                activities=activityMapper.selectAllActiviysByUserId(id);
            }else {
                activities = activityMapper.selectActiviysByUserId(id, state);
            }
        }finally {
            rlock.unlock();
        }

        return activities;
    }
}
