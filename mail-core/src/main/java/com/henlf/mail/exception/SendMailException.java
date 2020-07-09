package com.henlf.mail.exception;

/**
 * 邮件发送异常
 * @author tanghongfeng
 */
public class SendMailException extends RuntimeException {
    public SendMailException() {
        super();
    }

    public SendMailException(String msg) {
        super(msg);
    }

    public SendMailException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
