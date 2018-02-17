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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    @ApiOperation(value="修改用户", notes="根据User对象修改用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public ApiResult updateUser(@RequestBody User user) throws Exception {
        UserElement ue=getCurrentUser();
        //用户id应该从后台获取，不能让前端传
        String id=ue.getUserId();
        user.setUId(id);
        user.setURole("user");//权限只能通过直接操作数据库修改
        userService.updateUser(user);
        return ResultUtil.success(user);
    }
    @ApiOperation(value="根据id查找用户", notes="根据id查用户")
    @RequestMapping(value = "/getUser",method = RequestMethod.POST)
    public ApiResult getUser(@RequestParam String openid) throws Exception {
        User user=userService.findUserById(openid);
        return ResultUtil.success(user);
    }
    @ApiOperation(value="根据id删除用户", notes="根据id删除用户")
    @RequestMapping(value = "/deleteUser/{id}",method = RequestMethod.GET)
    public ApiResult deleteUser(@PathVariable String id) throws Exception {
        UserElement ue=getCurrentUser();//这个方法应该是很多controller都可以用的，所以可以做一个BaseCOntroller
        if("admin".equals(ue.getRole())){
            userService.deleteUserById(id);//只有管理员能删
        }else {
           throw new GlowwormExecption(ResultEnum.NO_AUTHORITY);
        }
        return ResultUtil.success("删除成功");
    }

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult login(@RequestParam String code) throws IOException {
        String token=userService.login(code);
        return ResultUtil.success(token);
    }

}
