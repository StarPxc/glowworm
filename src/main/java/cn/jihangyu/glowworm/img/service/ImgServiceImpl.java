package cn.jihangyu.glowworm.img.service;

import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.utils.QiniuFileUploadUtil;
import cn.jihangyu.glowworm.img.dao.AdImgMapper;
import cn.jihangyu.glowworm.img.dao.CaImgMapper;
import cn.jihangyu.glowworm.img.entity.AdImg;
import cn.jihangyu.glowworm.img.entity.CaImg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static cn.jihangyu.glowworm.common.utils.QiniuFileUploadUtil.uploadBookImg;

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

    @Override
    public String uploadCaImg(MultipartFile file,String type,String text) throws IOException {
        if (file == null || type==null||text==null ) {
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        CaImg caimg=new CaImg();
        String img_type=type;
        String img_introduction=text;
        String img_url= QiniuFileUploadUtil.uploadBookImg(file);
        caimg.setUrl(img_url);
        caimg.setIntroduction(img_introduction);
        caimg.setType(img_type);
        try{
        caImgMapper.insertSelective(caimg);
        return img_url;
        }catch (Exception e){
            throw new GlowwormExecption(ResultEnum.FILE_ERROR);
        }
    }

    @Override
    public String uploadAdImg(MultipartFile file, String size, String text) throws IOException {
        if (file == null || size==null||text==null ) {
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        AdImg adImg=new AdImg();
        String img_size=size;
        String img_introduction=text;
        String img_url=QiniuFileUploadUtil.uploadBookImg(file);
        adImg.setUrl(img_url);
        adImg.setSize(img_size);
        adImg.setIntroduction(img_introduction);
        try{
            adImgMapper.insertSelective(adImg);
            return img_url;
        }catch (Exception e){
            throw new GlowwormExecption(ResultEnum.FILE_ERROR);
        }
    }
}
