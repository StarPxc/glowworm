package cn.jihangyu.glowworm.user.controller;

import cn.jihangyu.glowworm.common.base.BaseController;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.user.entity.User;
import cn.jihangyu.glowworm.user.entity.UserElement;
import cn.jihangyu.glowworm.user.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.user.controller
 * @Description: TODO
 * @date 2018/2/2 9:07
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController{
    @Autowired
    @Qualifier("userServiceImpl")
    @Lazy
    private UserService userService;

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public ApiResult addUser(@RequestBody User user) throws Exception {
//        if("admin".equals(user.getURole())){
//            throw new GlowwormExecption(ResultEnum.NO_AUTHORITY);
//        }
        String token=userService.addUser(user);
        return ResultUtil.success(token);
    }
    @ApiOperation(value="修改用户", notes="根据User对象修改用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public ApiResult updateUser(@RequestBody User user) throws Exception {
        if(user.getUId()==null){
            throw new GlowwormExecption(ResultEnum.NO_ID);
        }
        int id=0;
        UserElement ue=getCurrentUser();//这个方法应该是很多controller都可以用的，所以可以做一个BaseCOntroller
        if("admin".equals(ue.getRole())){
            id=user.getUId();//如果是管理员，可以修改任何用户
        }else {
            //用户id应该从后台获取，不能让前端传
            id=ue.getUserId();//普通用户只能修改自己
        }
        user.setUId(id);
        userService.updateUser(user);
        return ResultUtil.success(user);
    }
    @ApiOperation(value="根据id查找用户", notes="根据id查用户")
    @RequestMapping(value = "/getUser/{id}",method = RequestMethod.GET)
    public ApiResult getUser(@PathVariable int id) throws Exception {

        User user=userService.findUserById(id);
        return ResultUtil.success(user);
    }
    @ApiOperation(value="根据id删除用户", notes="根据id删除用户")
    @RequestMapping(value = "/deleteUser/{id}",method = RequestMethod.GET)
    public ApiResult deleteUser(@PathVariable int id) throws Exception {
        UserElement ue=getCurrentUser();//这个方法应该是很多controller都可以用的，所以可以做一个BaseCOntroller
        if("admin".equals(ue.getRole())){
            userService.deleteUserById(id);//只有管理员能删
        }else {
           throw new GlowwormExecption(ResultEnum.NO_AUTHORITY);
        }
        return ResultUtil.success("删除成功");
    }




}
