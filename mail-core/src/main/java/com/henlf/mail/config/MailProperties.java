package com.henlf.mail.config;

import lombok.*;

/**
 * 邮件配置
 * @author tanghongfeng
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MailProperties {
    /**
     * 用户的认证方式
     */
    private Boolean auth = true;
    /**
     * 传输协议
     */
    private String protocol;
    /**
     * 发件人 SMTP 服务器地址
     */
    private String host;
    /**
     * 发件人 SMTP 服务器地址
     */
    private Integer port;
    /**
     * 是否开启 ssl
     */
    private Boolean ssl = true;
    /**
     * 发件人邮箱账号
     */
    private String account;
    /**
     * 发件人邮箱客户授权码，或邮箱登录密码
     */
    private String password;
}
