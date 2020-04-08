package com.lzw.controller;

import com.lzw.common.JsonData;
import com.lzw.param.AclModuleParam;
import com.lzw.param.DeptParam;
import com.lzw.service.SysAclModuleService;
import com.lzw.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;


@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Resource
    private SysAclModuleService sysAclModuleService;
    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/acl.page")
    public ModelAndView page(){
        return  new ModelAndView("acl");
    }

    @RequestMapping("/save.json")
    public JsonData saveAclModule(AclModuleParam param){
        sysAclModuleService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAcl(AclModuleParam param){
        sysAclModuleService.update(param);
        return  JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        return  JsonData.success(sysTreeService.aclModuleTree());
    }

}
