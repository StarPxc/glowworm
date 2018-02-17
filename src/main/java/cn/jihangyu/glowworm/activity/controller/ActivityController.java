package cn.jihangyu.glowworm.activity.controller;

import cn.jihangyu.glowworm.activity.entity.Activity;
import cn.jihangyu.glowworm.activity.service.ActivityService;
import cn.jihangyu.glowworm.common.base.BaseController;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.user.entity.UserElement;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.activity.controller
 * @Description: TODO
 * @date 2018/2/3 17:06
 */
@RestController
@RequestMapping("activity")
public class ActivityController extends BaseController{

    @Autowired
    @Qualifier("activityServiceImpl")
    private ActivityService activityService;

    @ApiOperation(value = "根据id获取活动", notes = "根据id获取活动")
    @RequestMapping(value = "/getActivity/{id}", method = RequestMethod.GET)
    public ApiResult getActivityByActivityId(@PathVariable Integer id) throws Exception {
        Activity activity = activityService.getActivityByActivityId(id);
        return ResultUtil.success(activity);
    }

    @ApiOperation(value = "创建活动", notes = "根据活动对象创建用户")
    @ApiImplicitParam(name = "activity", value = "活动详细实体activity", required = true, dataType = "Activity")
    @RequestMapping(value = "/addActivity", method = RequestMethod.POST)
    public ApiResult addActivity(@RequestBody Activity activity) throws Exception {
        Activity activity1=null;
        UserElement ue=getCurrentUser();
        if("admin".equals(ue.getRole())){
            activity1= activityService.addActivity(activity);//管理员才可以创建
            return ResultUtil.success(activity1);
        }else {
            throw new GlowwormExecption(ResultEnum.NO_AUTHORITY);
        }

    }

    @ApiOperation(value = "修改活动", notes = "根据活动对象修改用户")
    @ApiImplicitParam(name = "activity", value = "活动详细实体activity", required = true, dataType = "Activity")
    @RequestMapping(value = "/updateActivity", method = RequestMethod.POST)
    public ApiResult updateActivity(@RequestBody Activity activity) throws Exception {
        activityService.updateActivity(activity);
        return ResultUtil.success(activity);
    }

    @ApiOperation(value = "根据id删除活动", notes = "根据id删除活动")
    @RequestMapping(value = "/deleteActivity/{id}", method = RequestMethod.GET)
    public ApiResult deleteActivity(@PathVariable Integer id) throws Exception {
        UserElement ue=getCurrentUser();
        if("admin".equals(ue.getRole())){
            activityService.deleteActivityById(id);
            return ResultUtil.success("删除成功");
        }else {
            throw new GlowwormExecption(ResultEnum.NO_AUTHORITY);
        }

    }

    @ApiOperation(value = "查找不同类型的活动", notes = "{1：未进行，2：正在进行，3：已结束，0:所有)")
    @RequestMapping(value = "/findActiviysByState/{state}", method = RequestMethod.GET)
    public ApiResult findActiviysByState(@PathVariable int state) throws Exception {
        List<Activity> activities = activityService.findActiviysByState(state);
        return ResultUtil.success(activities);
    }
    @ApiOperation(value = "批量上传活动图片", notes = "批量上传活动图片")
    @RequestMapping(value = "/uploadBatch", method = RequestMethod.POST)
    public ApiResult uploadBatch(@RequestParam("files") MultipartFile[] files, @RequestParam Integer aId) throws IOException {
        String [] resultFileNames=activityService.uploadBatch(files,aId);
        return ResultUtil.success(resultFileNames);
        
    }
    @ApiOperation(value = "上传单个活动图片", notes = "上传单个活动图片")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ApiResult upload(@RequestParam("file") MultipartFile file, @RequestParam Integer aId) throws IOException {
            String resultFileName=activityService.upload(file,aId);
            return ResultUtil.success(resultFileName);
    }

}
