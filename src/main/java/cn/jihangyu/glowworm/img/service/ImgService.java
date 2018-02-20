package cn.jihangyu.glowworm.img.service;

import cn.jihangyu.glowworm.img.entity.AdImg;
import cn.jihangyu.glowworm.img.entity.CaImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImgService {
    List<CaImg> getCaImg(String type);
    List<AdImg> getAdImg(String size);
    String uploadCaImg(MultipartFile img,String type,String text) throws IOException;

    String uploadAdImg(MultipartFile file, String size, String text) throws IOException;
}
