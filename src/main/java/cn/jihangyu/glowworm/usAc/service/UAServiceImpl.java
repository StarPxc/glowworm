package cn.jihangyu.glowworm.usAc.service;

import cn.jihangyu.glowworm.activity.dao.ActivityMapper;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.utils.MyUtil;
import cn.jihangyu.glowworm.usAc.dao.UsAcMapper;
import cn.jihangyu.glowworm.usAc.entity.UsAc;
import cn.jihangyu.glowworm.user.dao.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if(usAc==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        if(MyUtil.isAllFieldNull(usAc)){
            throw new GlowwormExecption(ResultEnum.OBJECT_ALL_Field_NULL);
        }
        if(userMapper.selectByPrimaryKey(usAc.getuId())==null){
            throw new GlowwormExecption(ResultEnum.NO_USER);
        }
        if(activityMapper.selectByPrimaryKey(usAc.getaId())==null){
            throw new GlowwormExecption(ResultEnum.NO_ACTIVITY);
        }
        try {
            usAcMapper.insertSelective(usAc);
        }catch (Exception e){
            log.error("【添加对象失败】",e);
            throw new GlowwormExecption(ResultEnum.OBJECT_ADD_ERROR);
        }

        return usAc;
    }
}
