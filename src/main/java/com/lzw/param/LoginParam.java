package com.lzw.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LoginParam {
    @NotBlank(message = "用户名不可以为空")
    @Length(max = 15 ,min = 1 ,message = "用户名不能小于1个字节和大于15个字节")
    private String username;

    @NotBlank(message = "密码不可以为空")
    @Length(max = 15 ,min = 1 ,message = "密码不能小于1个字节和大于15个字节")
    private String password;

}
