package cn.jihangyu.glowworm.userRemarkActivity.controller;

import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.userRemarkActivity.entity.UserRemarkActivity;
import cn.jihangyu.glowworm.userRemarkActivity.service.UserRemarkActivityService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.userRemarkActivity.controller
 * @Description: TODO
 * @date 2018/2/5 16:39
 */
@RestController
@RequestMapping("userRemarkActivity")
public class UserRemarkActivityController {
    @Autowired
    @Qualifier("userRemarkActivityServiceImpl")
    private UserRemarkActivityService userRemarkActivityService;
    @ApiOperation(value = "添加用户评论活动的一条记录",notes = "添加用户评论活动的一条记录")
    @ApiImplicitParam(name = "userRemarkActivity",value = "用户评价活动类实体",required = true,dataType = "UserRemarkActivity")
    @RequestMapping(value = "/remark",method = RequestMethod.POST)
    public ApiResult remark(@RequestBody UserRemarkActivity userRemarkActivity) throws Exception {
       UserRemarkActivity userRemarkActivity1= userRemarkActivityService.addComment(userRemarkActivity);
        return ResultUtil.success(userRemarkActivity1);
    }
    @ApiOperation(value="根据id删除用户评论活动的一条记录", notes="根据id删除用户评论活动的一条记录")
    @RequestMapping(value = "/deleteUserRemarkActivity/{id}",method = RequestMethod.GET)
    public ApiResult deleteUserRemarkActivity(@PathVariable int id) throws Exception {
        userRemarkActivityService.deleteUserRemarkActivityById(id);
        return ResultUtil.success("删除成功");
    }
    @ApiOperation(value="根据用户id和活动id查看用户评论活动的记录", notes="根据用户id和活动id查看用户评论活动的记录id是0表示所有")
    @RequestMapping(value = "/findUserRemarkActivityByUIdAndAId/{uId}/{aId}",method = RequestMethod.GET)
    public ApiResult findUserRemarkActivityByUIdAndAId(@PathVariable Integer uId,@PathVariable Integer aId) throws Exception {
        List<UserRemarkActivity> userRemarkActivitys=userRemarkActivityService.selectUserRemarkActivitysByUIdAndAId(uId,aId);
        return ResultUtil.success(userRemarkActivitys);
    }
}
