package cn.jihangyu.glowworm.img.dao;

import cn.jihangyu.glowworm.img.entity.CaImg;

import java.util.List;

public interface CaImgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CaImg record);

    int insertSelective(CaImg record);

    CaImg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CaImg record);

    int updateByPrimaryKey(CaImg record);

    List<CaImg> selectCaImgByType(String type);
}