package cn.jihangyu.glowworm.img.controller;

import cn.jihangyu.glowworm.common.base.BaseController;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.img.entity.AdImg;
import cn.jihangyu.glowworm.img.entity.CaImg;
import cn.jihangyu.glowworm.img.service.ImgService;
import cn.jihangyu.glowworm.user.entity.UserElement;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("img")
@Slf4j
public class ImgController extends BaseController {
    @Autowired
    @Qualifier("imgServiceImpl")
    private ImgService imgService;

    @ApiOperation(value="获取某一种类轮播图", notes="获取轮播图")
    @RequestMapping(value = "/getCaImg/{type}",method = RequestMethod.GET)
    public ApiResult getCaImg(@PathVariable String type){
         List<CaImg> imgs=imgService.getCaImg(type);
        return ResultUtil.success(imgs);
    }

    @ApiOperation(value="获取某一尺寸广告图", notes="获取广告图")
    @RequestMapping(value = "/getAdImg/{size}",method = RequestMethod.GET)
    public ApiResult getAdImg(@PathVariable String  size){
        List<AdImg> imgs=imgService.getAdImg(size);
        return ResultUtil.success(imgs);
    }

    @ApiOperation(value="上传轮播图", notes="上传轮播图")
    @ApiImplicitParam(name = "caImg", value = "轮播图实体", required = true, dataType = "CaImg")
    @RequestMapping(value = "/uploadCaImg",method = RequestMethod.POST)
    public ApiResult uploadCaImg(@RequestParam(required=true) MultipartFile file,@RequestParam(required = true) String type,@RequestParam(required = true) String text) throws IOException {
        UserElement ue=getCurrentUser();
        if(ue.getRole().equals("admin")){
            String resultFileName=imgService.uploadCaImg(file,type,text);
            return ResultUtil.success(resultFileName);
        }else {
            return  ResultUtil.error(ResultEnum.NO_AUTHORITY.getCode(),ResultEnum.NO_AUTHORITY.getMsg());
        }

    }

    @ApiOperation(value="上传广告图", notes="上传广告图")
    @ApiImplicitParam(name = "adImg", value = "广告图实体", required = true, dataType = "AdImg")
    @RequestMapping(value = "/uploadAdImg",method = RequestMethod.POST)
    public ApiResult uploadAdImg(@RequestParam(required=true) MultipartFile file,@RequestParam(required = true) String size,@RequestParam(required = true) String text) throws IOException {
        UserElement ue=getCurrentUser();
        if(ue.getRole().equals("admin")){
            String resultFileName=imgService.uploadAdImg(file,size,text);
            return ResultUtil.success(resultFileName);
        }else{
            return  ResultUtil.error(ResultEnum.NO_AUTHORITY.getCode(),ResultEnum.NO_AUTHORITY.getMsg());
        }

    }
}
