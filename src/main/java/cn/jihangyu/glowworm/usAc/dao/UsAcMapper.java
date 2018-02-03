package cn.jihangyu.glowworm.usAc.dao;

import cn.jihangyu.glowworm.usAc.entity.UsAc;

public interface UsAcMapper {
    int deleteByPrimaryKey(Integer uaId);

    int insert(UsAc record);

    int insertSelective(UsAc record);

    UsAc selectByPrimaryKey(Integer uaId);

    int updateByPrimaryKeySelective(UsAc record);

    int updateByPrimaryKey(UsAc record);
}