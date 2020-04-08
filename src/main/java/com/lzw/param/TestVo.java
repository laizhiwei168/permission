package com.lzw.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class TestVo {
    @NotBlank(message = "msg不能为空")
    private String msg;

    @NotNull(message = "id不能为空")
    private Integer id;

   // @NotEmpty
    //private List<String> ls;

}
