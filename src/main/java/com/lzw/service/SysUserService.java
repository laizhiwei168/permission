package com.lzw.service;

import com.google.common.base.Preconditions;
import com.lzw.common.RequestHolder;
import com.lzw.dao.SysUserMapper;
import com.lzw.exception.ParamException;
import com.lzw.model.SysUser;
import com.lzw.param.UserParam;
import com.lzw.util.BeanValidator;
import com.lzw.util.IpUtil;
import com.lzw.util.MD5Util;
import com.lzw.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public void save(UserParam param){
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(),param.getId())){
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getMail(),param.getId())){
            throw new ParamException("邮箱已被占用");
        }

        String password = PasswordUtil.randomPassword();
        password = "123456";

        SysUser user=SysUser.builder().username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .password(MD5Util.encrypt(password)).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        user.setOperator(RequestHolder.getCurrentUser().getUsername()); //TODO;
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());

        // TODO: sendEmail

        sysUserMapper.insertSelective(user);
    }

    public void update(UserParam param){
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(),param.getId())){
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getMail(),param.getId())){
            throw new ParamException("邮箱已被占用");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的用户不存在");
        SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .password(before.getPassword()).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername()); //TODO;
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    // 检查邮箱是否存在
    public boolean checkEmailExist(String mail,Integer userId){
        return  sysUserMapper.countByMail(mail,userId)>0;
    }
    // 检查电话是否存在
    public boolean checkTelephoneExist(String telephone,Integer userId){
        return sysUserMapper.countByTelephone(telephone,userId)>0;
    }

    public SysUser findByKeyword(String keyword){
        // 使用账号或邮箱查询用户
        return sysUserMapper.findByKeyword(keyword);
    }
}
