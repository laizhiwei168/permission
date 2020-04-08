package com.lzw.controller;

import com.lzw.common.JsonData;
import com.lzw.common.RequestHolder;
import com.lzw.dto.DeptLevelDto;
import com.lzw.param.DeptParam;
import com.lzw.service.SysDeptService;
import com.lzw.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam dp){
        sysDeptService.save(dp);
        return  JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        System.out.println("RequestHolder.getCurrentUser().getUsername():"+ RequestHolder.getCurrentUser().getUsername());
        List<DeptLevelDto> dtoList=sysTreeService.deptTree();
        return  JsonData.success(dtoList);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData update(DeptParam dp){
        sysDeptService.update(dp);
        return  JsonData.success();
    }

}
