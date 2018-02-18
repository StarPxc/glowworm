package cn.jihangyu.glowworm.usBo.dao;

import cn.jihangyu.glowworm.usBo.entity.UsBo;

public interface UsBoMapper {
    int deleteByPrimaryKey(String uId);

    int insert(UsBo record);

    int insertSelective(UsBo record);

    UsBo selectByPrimaryKey(String uId);

    int updateByPrimaryKeySelective(UsBo record);

    int updateByPrimaryKey(UsBo record);
}