package cn.jihangyu.glowworm.usAc.controller;

import cn.jihangyu.glowworm.activity.entity.Activity;
import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.usAc.entity.UsAc;
import cn.jihangyu.glowworm.usAc.service.UAService;
import cn.jihangyu.glowworm.user.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.usAc.controller
 * @Description: TODO 用户和活动的关系
 * @date 2018/2/3 19:31
 */
@RestController
@RequestMapping("ua")
public class UAController {
    @Autowired
    @Qualifier("UAServiceImpl")
    private UAService uaService;

    @ApiOperation(value = "参加活动",notes = "参加活动")
    @RequestMapping(value = "/join",method = RequestMethod.POST)
    public ApiResult join(@RequestBody UsAc usAc) throws Exception {
        UsAc usAc1=uaService.addUsAc(usAc);
        return ResultUtil.success(usAc1);
    }
    @ApiOperation(value="根据id删除用户和活动的关系", notes="根据id删除活动")
    @RequestMapping(value = "/deleteUAById/{id}",method = RequestMethod.GET)
    public ApiResult deleteUAById(@PathVariable Integer id) throws Exception {
        uaService.deleteUAById(id);
        return ResultUtil.success("删除成功");
    }
    @ApiOperation(value="根据活动id获取报名这个活动的用户", notes="根据活动id获取报名这个活动的用户")
    @RequestMapping(value = "/findUsersByActivityId/{id}",method = RequestMethod.GET)
    public ApiResult findUsersByActivityId(@PathVariable Integer id) throws Exception {
        List<User> users =uaService.findUsersByActivityId(id);
        return ResultUtil.success(users);
    }
    @ApiOperation(value="根据用户id和活动状态查找他参加的活动", notes="{1：未进行，2：正在进行，3：已结束，0:所有)")
    @RequestMapping(value = "/findActiviysByUserId",method = RequestMethod.POST)
    public ApiResult findActiviysByUserId(@RequestParam String openid,@RequestParam int state) throws Exception {
        List<Activity> activities=uaService.findActiviysByUserId(openid,state);
        return ResultUtil.success(activities);
    }

}
