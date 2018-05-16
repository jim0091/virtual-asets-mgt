package com.jinhui.common.controller.vo;

/**
 * @autor wsc
 * @create 2018-04-23 9:41
 **/
public class UpdatePassVo {

    private String password;

    private String newPassword;

    private String confirmPassword;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
