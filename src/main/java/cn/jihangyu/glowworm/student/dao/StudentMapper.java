package cn.jihangyu.glowworm.student.dao;

import cn.jihangyu.glowworm.student.entity.Student;
import org.springframework.stereotype.Component;

@Component
public interface StudentMapper {
    int deleteByPrimaryKey(String s);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(String s);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}