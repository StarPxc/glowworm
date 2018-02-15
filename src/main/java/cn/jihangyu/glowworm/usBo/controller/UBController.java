package cn.jihangyu.glowworm.usBo.controller;

import cn.jihangyu.glowworm.book.entity.Book;
import cn.jihangyu.glowworm.common.base.BaseController;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.usBo.service.UBService;
import cn.jihangyu.glowworm.user.entity.UserElement;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("ub")
public class UBController extends BaseController{

    @Autowired
    @Qualifier("UBServiceImpl")
    private UBService ubService;

    @ApiOperation(value = "用户查询自己拥有的书",notes = "用户查询自己拥有的书")
    @RequestMapping(value = "/findAllHadBookByUid/{id}",method = RequestMethod.GET)
    public ApiResult findAllHadBookByUid(@PathVariable int id){
        UserElement ue=getCurrentUser();//用户只能查看自己拥有的书
        try {
            if ("admin".equals(ue.getRole())) {
                List<Book> list = ubService.findAllHadBookByUid(id);//只有管理员能查看任意用户拥有的书
                return ResultUtil.success(list);
            } else {
                Integer uid = ue.getUserId();
                List<Book> list = ubService.findAllHadBookByUid(uid);
                return ResultUtil.success(list);
            }
        }catch (Exception e){
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(),ResultEnum.OBJECT_FIND_ERROR.getMsg());
        }
    }

    @ApiOperation(value = "用户查询自己正在使用的书",notes = "用户查询自己正在使用的书")
    @RequestMapping(value = "/findAllHadBookByUid/{id}",method = RequestMethod.GET)
    public ApiResult findAllUsedBookByUid(@PathVariable int id){
        UserElement ue=getCurrentUser();//用户只能查看自己正在使用的书
        try {
            if ("admin".equals(ue.getRole())) {
                List<Book> list = ubService.findAllUsedBookByUid(id);//只有管理员能查看任意用户正在使用的书
                return ResultUtil.success(list);
            } else {
                Integer uid = ue.getUserId();
                List<Book> list = ubService.findAllUsedBookByUid(uid);
                return ResultUtil.success(list);
            }
        }catch (Exception e){
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(),ResultEnum.OBJECT_FIND_ERROR.getMsg());
        }
    }

}
