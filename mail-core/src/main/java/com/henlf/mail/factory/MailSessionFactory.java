package com.henlf.mail.factory;

import com.henlf.mail.config.MailProperties;
import com.henlf.mail.domain.VerifyMail;

import javax.mail.Session;
import java.util.Properties;

/**
 * 邮件 Session 工厂
 * @author tanghongfeng
 */
public class MailSessionFactory {
    static {
        // 邮件附件 .bin 问题
        System.setProperty("mail.mime.splitlongparameters", "false");
        System.setProperty("mail.mime.charset", "UTF-8");
    }

    /**
     * 获取 Session
     * @param mailProperties 邮件环境配置
     * @return Session
     */
    public static Session getSession(MailProperties mailProperties) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", mailProperties.getAuth().toString());
        props.put("mail.transport.protocol", mailProperties.getProtocol());
        props.put("mail.smtp.host", mailProperties.getHost());
        props.put("mail.smtp.port", mailProperties.getPort());
        props.put("mail.smtp.starrttls.enable", mailProperties.getSsl().toString());
        VerifyMail verifyMail = new VerifyMail(mailProperties.getAccount(), mailProperties.getPassword());
        return Session.getInstance(props, verifyMail);
    }
}
