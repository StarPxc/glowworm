package cn.jihangyu.glowworm.img.controller;

import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.img.entity.AdImg;
import cn.jihangyu.glowworm.img.entity.CaImg;
import cn.jihangyu.glowworm.img.service.ImgService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("img")
@Slf4j
public class ImgController {
    @Autowired
    @Qualifier("imgServiceImpl")
    private ImgService imgService;

    @ApiOperation(value="获取某一种类轮播图", notes="获取轮播图")
    @RequestMapping(value = "/getCaImg/{type}",method = RequestMethod.GET)
    public ApiResult getCaImg(String  type){
         List<CaImg> imgs=imgService.getCaImg(type);
        return ResultUtil.success(imgs);
    }

    @ApiOperation(value="获取某一尺寸广告图", notes="获取广告图")
    @RequestMapping(value = "/getCaImg/{size}",method = RequestMethod.GET)
    public ApiResult getAdImg(String  size){
        List<AdImg> imgs=imgService.getAdImg(size);
        return ResultUtil.success(imgs);
    }

    @ApiOperation(value="上传轮播图", notes="上传轮播图")
    @ApiImplicitParam(name = "caImg", value = "轮播图实体", required = true, dataType = "CaImg")
    @RequestMapping(value = "/uploadCaImg",method = RequestMethod.POST)
    public ApiResult uploadCaImg(HttpServletRequest req, @RequestParam(required=true ) MultipartFile img){

        return null;
    }

    @ApiOperation(value="上传广告图", notes="上传广告图")
    @ApiImplicitParam(name = "adImg", value = "广告图实体", required = true, dataType = "AdImg")
    @RequestMapping(value = "/uploadAdImg",method = RequestMethod.POST)
    public ApiResult uploadAdImg(HttpServletRequest req, @RequestParam(required=true ) MultipartFile img){

        return null;
    }
}
