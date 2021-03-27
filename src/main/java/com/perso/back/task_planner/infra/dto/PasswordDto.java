package com.perso.back.task_planner.infra.dto;

import com.perso.back.task_planner.aop.ValidPassword;

public class PasswordDto {

    private String oldPassword;

    private  String token;

    @ValidPassword
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
