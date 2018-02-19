package cn.jihangyu.glowworm.img.service;

import cn.jihangyu.glowworm.img.entity.AdImg;
import cn.jihangyu.glowworm.img.entity.CaImg;

import java.util.List;

public interface ImgService {
    List<CaImg> getCaImg(String type);

    List<AdImg> getAdImg(String size);
}
