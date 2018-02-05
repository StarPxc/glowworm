package cn.jihangyu.glowworm.user.controller;

import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.user.entity.User;
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
public class UserController {
    @Autowired
    @Qualifier("userServiceImpl")
    @Lazy
    private UserService userService;

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public ApiResult addUser(@RequestBody User user) throws Exception {
        User user1=userService.addUser(user);
        return ResultUtil.success(user1);
    }
    @ApiOperation(value="修改用户", notes="根据User对象修改用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public ApiResult updateUser(@RequestBody User user) throws Exception {
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
        userService.deleteUserById(id);
        return ResultUtil.success("删除成功");
    }




}
