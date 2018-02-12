package cn.jihangyu.glowworm.activity.service;

import cn.jihangyu.glowworm.activity.dao.ActivityMapper;
import cn.jihangyu.glowworm.activity.entity.Activity;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.utils.MyUtil;
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
 * @Package cn.jihangyu.glowworm.activity.service
 * @Description: TODO
 * @date 2018/2/3 17:07
 */
@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    private static ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private static Lock rlock = lock.readLock();
    private static Lock wlock = lock.writeLock();

    @Override
    public Activity getActivityByActivityId(Integer id) throws Exception {
        Activity activity;
        if (id == null) {
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        rlock.lock();
        try {
          activity= activityMapper.selectByPrimaryKey(id);
        } finally {
            rlock.unlock();
        }
        if (activity == null) {
            throw new GlowwormExecption(ResultEnum.NO_ACTIVITY);
        }
        return activity;
    }

    @Override
    public Activity addActivity(Activity activity) throws Exception {
        if (activity == null) {
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if (MyUtil.isAllFieldNull(activity)) {
            throw new GlowwormExecption(ResultEnum.OBJECT_ALL_Field_NULL);
        }
        wlock.lock();
        try {
            activityMapper.insertSelective(activity);
        } catch (Exception e) {
            log.error("【添加对象失败】", e);
            throw new GlowwormExecption(ResultEnum.OBJECT_ADD_ERROR);
        }finally {
            wlock.unlock();
        }
        return activity;
    }

    @Override
    public void updateActivity(Activity activity) {
        wlock.lock();
        try {
            int code = activityMapper.updateByPrimaryKeySelective(activity);
            if (code != 1) {
                throw new GlowwormExecption(ResultEnum.NO_ACTIVITY);
            }
        } catch (GlowwormExecption e) {
            throw new GlowwormExecption(ResultEnum.NO_ACTIVITY);
        } catch (Exception e) {
            log.error("【修改用户失败】", e);
            throw new GlowwormExecption(ResultEnum.OBJECT_UPDATE_ERROR);

        }finally {
            wlock.unlock();
        }
    }

    @Override
    public void deleteActivityById(Integer id) {
        if(id==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        wlock.lock();
        try {
            int code = activityMapper.deleteByPrimaryKey(id);
            if (code != 1) {
                throw new GlowwormExecption(ResultEnum.NO_ACTIVITY);
            }
        } catch (GlowwormExecption e) {
            throw new GlowwormExecption(ResultEnum.NO_ACTIVITY);
        } catch (Exception e) {
            log.error("【删除对象失败】", e);
            throw new GlowwormExecption(ResultEnum.OBJECT_DELETE_ERROR);

        }finally {
            wlock.unlock();
        }
    }

    @Override
    public List<Activity> findActiviysByState(Integer state) {
        List<Activity> activities;
        if (state == null) {
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        rlock.lock();
        try {
            if (state == 0) {
                activities = activityMapper.selectAllActiviys();
            } else {
                activities = activityMapper.selectActiviysByState(state);
            }
        }finally {
            rlock.unlock();
        }

        if (activities == null) {
            throw new GlowwormExecption(ResultEnum.NO_ACTIVITY);
        }
        return activities;
    }

}
