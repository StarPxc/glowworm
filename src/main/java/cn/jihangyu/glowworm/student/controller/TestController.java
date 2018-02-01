package cn.jihangyu.glowworm.student.controller;

import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.MyUtil;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.student.entity.Student;
import cn.jihangyu.glowworm.student.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.student.controller
 * @Description: TODO
 * @date 2018/2/1 17:00
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    @Qualifier("testServiceImpl")
    private TestService testService;
    @RequestMapping("/testInsert")
    public ApiResult testInsert(@RequestBody Student student) throws Exception {
        if(student==null|| MyUtil.isAllFieldNull(student)){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        testService.add(student);
        return ResultUtil.success(student);
    }

}
