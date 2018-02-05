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

    @Override
    public UsAc addUsAc(UsAc usAc) throws Exception {
        if (usAc == null) {
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if (MyUtil.isAllFieldNull(usAc)) {
            throw new GlowwormExecption(ResultEnum.OBJECT_ALL_Field_NULL);
        }
        if (userMapper.selectByPrimaryKey(usAc.getuId()) == null) {
            throw new GlowwormExecption(ResultEnum.NO_USER);
        }
        if (activityMapper.selectByPrimaryKey(usAc.getaId()) == null) {
            throw new GlowwormExecption(ResultEnum.NO_ACTIVITY);
        }
        try {

            usAcMapper.insertSelective(usAc);
        } catch (Exception e) {
            log.error("【参加活动失败】", e);
            throw new GlowwormExecption(ResultEnum.OBJECT_ADD_ERROR);
        }

        return usAc;
    }

    @Override
    public void deleteUAById(Integer id) {
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

        }
    }

    @Override
    public List<User> findUsersByActivityId(Integer id) {
        if (id == null) {
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        List<User> users = userMapper.selectUsersByActivityId(id);
        return users;
    }
    @Override
    public List<Activity> findActiviysByUserId(Integer id, Integer state) {
        List<Activity> activities;
        if(id==null||state==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if(state==0){
            activities=activityMapper.selectAllActiviysByUserId(id);
        }else {
            activities = activityMapper.selectActiviysByUserId(id, state);
        }
        if (activities == null) {
            throw new GlowwormExecption(ResultEnum.NO_USER);
        }
        return activities;
    }
}
