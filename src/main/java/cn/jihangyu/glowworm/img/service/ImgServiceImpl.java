package cn.jihangyu.glowworm.img.service;

import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.img.dao.AdImgMapper;
import cn.jihangyu.glowworm.img.dao.CaImgMapper;
import cn.jihangyu.glowworm.img.entity.AdImg;
import cn.jihangyu.glowworm.img.entity.CaImg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ImgServiceImpl implements ImgService {
    @Autowired
    private AdImgMapper adImgMapper;
    @Autowired
    private CaImgMapper caImgMapper;

    @Override
    public List<CaImg> getCaImg(String type) {
        List<CaImg> imgs=null;
        try{
             imgs=caImgMapper.selectCaImgByType(type);
        }catch (Exception e){
            throw new GlowwormExecption(ResultEnum.IMG_ERROR);
        }
        return imgs;
    }

    @Override
    public List<AdImg> getAdImg(String size) {
        List<AdImg> imgs=null;
        try{
            imgs=adImgMapper.selectAdImgBySize(size);
        }catch (Exception e){
            throw new GlowwormExecption(ResultEnum.IMG_ERROR);
        }
        return imgs;
    }
}
