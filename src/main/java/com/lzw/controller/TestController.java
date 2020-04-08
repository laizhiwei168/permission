package com.lzw.controller;

import com.lzw.common.ApplicationContextHelper;
import com.lzw.common.JsonData;
import com.lzw.exception.ParamException;
import com.lzw.exception.PermissionException;
import com.lzw.param.TestVo;
import com.lzw.util.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello(){
        log.info("hello");
        throw  new PermissionException("test exception");
        //return JsonData.success("hello,permission") ;
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) throws ParamException {
        log.info("validate");
        //SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean()
        BeanValidator.check(vo);
        return JsonData.success("validate,permission") ;
    }
}
