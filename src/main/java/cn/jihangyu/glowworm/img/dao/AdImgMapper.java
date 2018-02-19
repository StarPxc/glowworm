package cn.jihangyu.glowworm.img.dao;

import cn.jihangyu.glowworm.img.entity.AdImg;

import java.util.List;

public interface AdImgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdImg record);

    int insertSelective(AdImg record);

    AdImg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdImg record);

    int updateByPrimaryKey(AdImg record);

    List<AdImg> selectAdImgBySize(String size);
}