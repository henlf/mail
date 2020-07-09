package com.henlf.mail.domain;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 验证
 * @author tanghongfeng
 */
public class VerifyMail extends Authenticator {
    private String userName;
    private String password;

    public VerifyMail(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}