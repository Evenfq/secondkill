package com.fanqiao.secondkill.vo;

import com.fanqiao.secondkill.validator.IsMobile;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    private String password;
}