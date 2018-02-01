package cn.jihangyu.glowworm.student.service;

import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.student.dao.StudentMapper;
import cn.jihangyu.glowworm.student.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.student.service
 * @Description: TODO
 * @date 2018/2/1 17:00
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {
    @Autowired
    private StudentMapper mapper;
    @Override
    public void add(Student student) {
        if(student==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        try {
            mapper.insertSelective(student);
        }catch (Exception e){
            log.error("【系统错误】",e);
        }

    }
}
