package cn.jihangyu.glowworm.usBo.controller;

import cn.jihangyu.glowworm.book.entity.Book;
import cn.jihangyu.glowworm.common.base.BaseController;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.usBo.entity.UsBo;
import cn.jihangyu.glowworm.usBo.service.UBService;
import cn.jihangyu.glowworm.user.entity.UserElement;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ub")
public class UBController extends BaseController {

    @Autowired
    @Qualifier("UBServiceImpl")
    private UBService ubService;

    @ApiOperation(value = "用户查询自己拥有的书", notes = "用户查询自己拥有的书")
    @RequestMapping(value = "/findAllHadBookByUid/{id}", method = RequestMethod.GET)
    public ApiResult findAllHadBookByUid(@PathVariable String id) {
        UserElement ue = getCurrentUser();//用户只能查看自己拥有的书
        try {
            if ("admin".equals(ue.getRole())) {
                List<Book> list = ubService.findAllHadBookByUid(id);//只有管理员能查看任意用户拥有的书
                return ResultUtil.success(list);
            } else {
                String uid = ue.getUserId();
                List<Book> list = ubService.findAllHadBookByUid(uid);
                return ResultUtil.success(list);
            }
        } catch (Exception e) {
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(), ResultEnum.OBJECT_FIND_ERROR.getMsg());
        }
    }

    @ApiOperation(value = "用户查询自己正在使用的书", notes = "用户查询自己正在使用的书")
    @RequestMapping(value = "/findAllUsedBookByUid/{id}", method = RequestMethod.GET)
    public ApiResult findAllUsedBookByUid(@PathVariable String id) {
        UserElement ue = getCurrentUser();//用户只能查看自己正在使用的书
        try {
            if ("admin".equals(ue.getRole())) {
                List<Book> list = ubService.findAllUsedBookByUid(id);//只有管理员能查看任意用户正在使用的书
                return ResultUtil.success(list);
            } else {
                String uid = ue.getUserId();
                List<Book> list = ubService.findAllUsedBookByUid(uid);
                return ResultUtil.success(list);
            }
        } catch (Exception e) {
            return ResultUtil.error(ResultEnum.OBJECT_FIND_ERROR.getCode(), ResultEnum.OBJECT_FIND_ERROR.getMsg());
        }
    }

    @ApiOperation(value = "用户预约某本书", notes = "用户预约某本书")
    @RequestMapping(value = "/orderBook", method = RequestMethod.POST)
    public ApiResult orderBook(@RequestParam Integer bId) {
        UserElement ue = getCurrentUser();
        UsBo usBo = new UsBo();
        usBo.setbId(bId);
        usBo.setuId(ue.getUserId());
        String result = ubService.orderBook(usBo);
        return ResultUtil.success(result);
    }

    @ApiOperation(value = "用户归还某本书", notes = "用户归还某本书")
    @RequestMapping(value = "/returnBook", method = RequestMethod.POST)
    public ApiResult returnBook(@RequestParam Integer bId) {
        UserElement ue = getCurrentUser();
        UsBo usBo = new UsBo();
        usBo.setbId(bId);
        usBo.setuId(ue.getUserId());
        String result = ubService.returnBook(usBo);
        return ResultUtil.success(result);
    }

}
