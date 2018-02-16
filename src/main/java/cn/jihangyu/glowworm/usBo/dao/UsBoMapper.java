package cn.jihangyu.glowworm.usBo.dao;

import cn.jihangyu.glowworm.usBo.entity.UsBo;

public interface UsBoMapper {
    int deleteByPrimaryKey(Integer uId);

    int insert(UsBo record);

    int insertSelective(UsBo record);

    UsBo selectByPrimaryKey(Integer uId);

    int updateByPrimaryKeySelective(UsBo record);

    int updateByPrimaryKey(UsBo record);
}