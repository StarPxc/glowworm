package cn.jihangyu.glowworm.usAc.controller;

import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import cn.jihangyu.glowworm.usAc.entity.UsAc;
import cn.jihangyu.glowworm.usAc.service.UAService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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
}
