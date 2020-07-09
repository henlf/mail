package com.henlf.mail;

import com.henlf.mail.callback.MailCallback;
import com.henlf.mail.config.MailProperties;
import com.henlf.mail.domain.Mail;
import com.henlf.mail.exception.SendMailException;
import com.henlf.mail.factory.MailSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 邮件发送
 * @author tanghongfeng
 */
public abstract class AbstractSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSender.class);

    private final MailProperties mailProperties;

    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(), r -> new Thread(r, "SendMailThread-" + threadNumber.getAndIncrement()));

    public AbstractSender(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    /**
     * 发送邮件
     * @param mail 邮件实体对象
     * @throws SendMailException
     */
    public void sendMail(Mail mail) {
        Session session = MailSessionFactory.getSession(mailProperties);

        try(Transport transport = session.getTransport();) {
            transport.connect(mailProperties.getAccount(), mailProperties.getPassword());

            Message msg = getMimeMessage(session, mail);

            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (NoSuchProviderException ex) {
            LOGGER.error("no provider exist for protocol:{}", mailProperties.getProtocol(), ex);
            throw new SendMailException(String.format("no provider exist for protocol:%s", mailProperties.getProtocol()));
        } catch (MessagingException ex) {
            LOGGER.error("occur errors when connect mail server, username:{}, password:{}",
                    mailProperties.getAccount(),mailProperties.getPassword(), ex);
            throw new SendMailException(String.format("fail to connect mail server, username:%s, password:%s",
                    mailProperties.getAccount(), mailProperties.getPassword()));
        }
    }

    /**
     * 异步发送邮件
     * @param mail
     * @param mailCallback
     */
    public void sendMailAsync(final Mail mail, final MailCallback mailCallback) {
        executor.submit(() -> {
            Session session = MailSessionFactory.getSession(mailProperties);
            try(Transport transport = session.getTransport();) {
                transport.connect(mailProperties.getAccount(), mailProperties.getPassword());

                Message msg = getMimeMessage(session, mail);

                transport.sendMessage(msg, msg.getAllRecipients());

                mailCallback.onSuccess();
            } catch (MessagingException ex) {
                mailCallback.onError(ex);
            }
        });
    }

    /**
     * 构建邮件消息
     * @param session
     * @param mail
     * @return
     */
    protected abstract Message getMimeMessage(Session session, Mail mail);

    protected String encode(String text) {
        try {
            return MimeUtility.encodeText(text, StandardCharsets.UTF_8.displayName(), "B");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("UTF-8 charset not supported", e);
            throw new SendMailException("UTF-8 charset not supported");
        }
    }
}
