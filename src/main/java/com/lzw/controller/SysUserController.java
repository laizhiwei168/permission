package com.lzw.controller;

import com.lzw.common.JsonData;
import com.lzw.param.UserParam;
import com.lzw.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    // http://localhost:9080/sys/user/save.json?username=lzw&telephone=13712943266&mail=lzw@qq.com&deptId=1&status=1&remark=ok

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam param){
        sysUserService.save(param);
        return  JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam param){
        sysUserService.save(param);
        return  JsonData.success();
    }
}
