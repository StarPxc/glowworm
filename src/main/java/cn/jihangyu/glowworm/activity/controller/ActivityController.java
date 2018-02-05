package cn.jihangyu.glowworm.activity.controller;

import cn.jihangyu.glowworm.activity.entity.Activity;
import cn.jihangyu.glowworm.activity.service.ActivityService;
import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.user.entity.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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
public class ActivityController {
    @Autowired
    @Qualifier("activityServiceImpl")
    private ActivityService activityService;

    @ApiOperation(value = "根据id获取活动",notes = "根据id获取活动")
    @RequestMapping(value = "/getActivity/{id}",method = RequestMethod.GET)
    public ApiResult getActivityByActivityId(@PathVariable Integer id) throws Exception {
        Activity activity=activityService.getActivityByActivityId(id);
        return ResultUtil.success(activity);
    }
    @ApiOperation(value="创建活动", notes="根据活动对象创建用户")
    @ApiImplicitParam(name = "activity", value = "活动详细实体activity", required = true, dataType = "Activity")
    @RequestMapping(value = "/addActivity",method = RequestMethod.POST)
    public ApiResult addActivity(@RequestBody Activity activity) throws Exception {
        Activity activity1=activityService.addActivity(activity);
        return ResultUtil.success(activity1);
    }
    @ApiOperation(value="修改活动", notes="根据活动对象修改用户")
    @ApiImplicitParam(name = "activity", value = "活动详细实体activity", required = true, dataType = "Activity")
    @RequestMapping(value = "/updateActivity",method = RequestMethod.POST)
    public ApiResult updateActivity(@RequestBody Activity activity) throws Exception {
        activityService.updateActivity(activity);
        return ResultUtil.success(activity);
    }
    @ApiOperation(value="根据id删除活动", notes="根据id删除活动")
    @RequestMapping(value = "/deleteActivity/{id}",method = RequestMethod.GET)
    public ApiResult deleteActivity(@PathVariable Integer id) throws Exception {
        activityService.deleteActivityById(id);
        return ResultUtil.success("删除成功");
    }
    @ApiOperation(value="查找不同类型的活动", notes="{1：未进行，2：正在进行，3：已结束，0:所有)")
    @RequestMapping(value = "/findActiviysByState/{state}",method = RequestMethod.GET)
    public ApiResult findActiviysByState(@PathVariable int state) throws Exception {
        List<Activity> activities=activityService.findActiviysByState(state);
        return ResultUtil.success(activities);
    }


}
