package com.henlf.mail.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author flex
 */
@ConfigurationProperties(prefix = "flex.mail")
public class MailConfigProperties {
    /**
     * 用户的认证方式
     */
    private boolean auth = true;
    /**
     * 传输协议
     */
    private String protocol = "smtp";
    /**
     * 发件人 SMTP 服务器地址
     */
    private String host;
    /**
     * 发件人 SMTP 服务器地址
     */
    private Integer port = 25;
    /**
     * 是否开启 ssl
     */
    private boolean ssl = true;
    /**
     * 发件人邮箱账号
     */
    private String account;
    /**
     * 发件人邮箱客户授权码，或邮箱登录密码
     */
    private String password;

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
