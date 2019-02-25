package com.fanqiao.secondkill.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "secondkill_user")
@Data
public class SecondkillUser {
    @Id
    private Long id;

    private String nickname;

    private String mobile;

    /**
     * MD5(MD5(password明文 + 固定salt) + salt)
     */
    private String password;

    private String salt;

    @Column(name = "regist_date")
    private Date registDate;

    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @Column(name = "login_count")
    private Integer loginCount;


}