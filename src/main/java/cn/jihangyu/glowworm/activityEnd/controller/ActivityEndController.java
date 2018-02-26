package cn.jihangyu.glowworm.activityEnd.controller;

import cn.jihangyu.glowworm.activityEnd.Service.ActivityEndService;
import cn.jihangyu.glowworm.activityEnd.entity.ActivityEnd;
import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.activityEnd.controller
 * @Description: TODO 结束活动
 * @date 2018/2/24 10:47
 */
@RestController
@RequestMapping("/activityEnd")
public class ActivityEndController {
    @Autowired
    @Qualifier("activityEndServiceImpl")
    private ActivityEndService activityEndService;
    @RequestMapping(value = "/stopActivityEnd",method = RequestMethod.POST)
    @ApiOperation(value = "结束活动", notes = "结束活动")
    @ApiImplicitParam(name = "activityEnd", value = "实体对象", required = true, dataType = "ActivityEnd")
    public ApiResult stopActivity(@RequestBody ActivityEnd activityEnd){
        activityEndService.stopActivityEnd(activityEnd);
        return ResultUtil.success("活动结束成功");
    }

    @RequestMapping(value = "/findActivityEnd/{id}" ,method = RequestMethod.GET)
    @ApiOperation(value = "根据id查看结束的活动", notes = "根据id查看结束的活动")
    public ApiResult findActivity(@PathVariable Integer id){
        ActivityEnd activityEnd=activityEndService.findActivityEnd(id);
        return ResultUtil.success(activityEnd);
    }
    @RequestMapping(value = "/findAllActivityEnd" ,method = RequestMethod.GET)
    @ApiOperation(value = "查看所有结束的活动", notes = "查看所有结束的活动")
    public ApiResult findAllActivity(){
        List<ActivityEnd> activityEnds=activityEndService.findAllActivityEnd();
        return ResultUtil.success(activityEnds);
    }
}
