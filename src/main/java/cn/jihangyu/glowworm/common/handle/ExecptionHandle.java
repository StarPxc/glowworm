package cn.jihangyu.glowworm.common.handle;

import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.resp.ApiResult;
import cn.jihangyu.glowworm.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Ethanp
 * @version V1.0
 * @Package com.db.fiftysql.common.execption
 * @Description: TODO
 * @date 2018/1/29 10:10
 */
@ControllerAdvice
@Slf4j
public class ExecptionHandle {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResult handle(Exception e ){
        if(e instanceof GlowwormExecption){
            GlowwormExecption glowwormExecption=(GlowwormExecption)e;
            return ResultUtil.error(glowwormExecption.getCode(),glowwormExecption.getMessage());
        }else {
            log.error("【系统异常】",e);
            return ResultUtil.error(ResultEnum.UNKONW_ERROR.getCode(),ResultEnum.UNKONW_ERROR.getMsg());
        }

    }
}
