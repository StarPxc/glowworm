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

    @Override
    public Activity getActivityByActivityId(Integer id) throws Exception {
        if(id==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        Activity activity=activityMapper.selectByPrimaryKey(id);
        if(MyUtil.isAllFieldNull(activity)){
            throw new GlowwormExecption(ResultEnum.OBJECT_ALL_Field_NULL);
        }
        return activity;
    }

    @Override
    public Activity addActivity(Activity activity) throws Exception {
        if(activity==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if(MyUtil.isAllFieldNull(activity)){
            throw new GlowwormExecption(ResultEnum.OBJECT_ALL_Field_NULL);
        }
        try {
            activityMapper.insertSelective(activity);
        }catch (Exception e){
            log.error("【添加对象失败】",e);
            throw new GlowwormExecption(ResultEnum.OBJECT_ADD_ERROR);
        }
        return activity;
    }

    @Override
    public void updateActivity(Activity activity) {
        try {
            int code= activityMapper.updateByPrimaryKeySelective(activity);
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
    public void deleteActivityById(Integer id) {
        try {
            int code= activityMapper.deleteByPrimaryKey(id);
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

    @Override
    public List<Activity> findActiviysByState(Integer state) {
        List<Activity> activities;
        if (state == null) {
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if (state == 0) {
            activities = activityMapper.selectAllActiviys();
        } else {
            activities = activityMapper.selectActiviysByState(state);
        }
        if (activities == null) {
            throw new GlowwormExecption(ResultEnum.NO_ACTIVITY);
        }
        return activities;
    }

}
