package cn.jihangyu.glowworm.activityEnd.Service;

import cn.jihangyu.glowworm.activity.dao.ActivityMapper;
import cn.jihangyu.glowworm.activity.entity.Activity;
import cn.jihangyu.glowworm.activityEnd.dao.ActivityEndMapper;
import cn.jihangyu.glowworm.activityEnd.entity.ActivityEnd;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.activityEnd.Service
 * @Description: TODO
 * @date 2018/2/24 10:50
 */
@Service
public class ActivityEndServiceImpl implements ActivityEndService {
    @Autowired
    private ActivityEndMapper activityEndMapper;
    @Autowired
    private ActivityMapper activityMapper;
    @Override
    public void stopActivityEnd(ActivityEnd activityEnd) {
        if(activityEnd==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if(activityMapper.selectByPrimaryKey(activityEnd.getAId())==null){
            throw new GlowwormExecption(ResultEnum.NO_ACTIVITY);
        }
        Activity activity=new Activity();
        activity.setAId(activityEnd.getAId());
        activity.setAState("3");
        activityMapper.updateByPrimaryKeySelective(activity);
        activityEndMapper.insertSelective(activityEnd);
    }

    @Override
    public ActivityEnd findActivityEnd(Integer id) {
        if(id==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        ActivityEnd activityEnd=activityEndMapper.selectByPrimaryKey(id);
        if(activityEnd==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_NULL);
        }
        Activity activity=activityMapper.selectByPrimaryKey(activityEnd.getAId());
        activityEnd.setActivity(activity);
        return activityEnd;
    }

    @Override
    public  List<ActivityEnd> findAllActivityEnd() {

        List<ActivityEnd> activityEnds=activityEndMapper.selectAll();
        for (int i = 0; i < activityEnds.size(); i++) {
            Activity activity=activityMapper.selectByPrimaryKey(activityEnds.get(i).getAId());
            activityEnds.get(i).setActivity(activity);
        }
        return activityEnds;
    }
}
