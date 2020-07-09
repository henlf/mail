package com.henlf.mail;

import com.henlf.mail.config.MailProperties;
import com.henlf.mail.domain.Mail;
import com.henlf.mail.exception.SendMailException;
import com.henlf.mail.util.CollectionUtil;
import com.henlf.mail.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tanghongfeng
 */
public class DefaultSender extends AbstractSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSender.class);

    public DefaultSender(MailProperties mailProperties) {
        super(mailProperties);
    }

    @Override
    protected Message getMimeMessage(Session session, Mail mail) {
        MimeMessage msg = new MimeMessage(session);


        Mail.MailMessageHeader messageHeader = mail.getMailMessageHeader();
        Mail.MailMessageBody messageBody = mail.getMailMessageBody();

        try {
            msg.setFrom(new InternetAddress(messageHeader.getFrom()));
            msg.addRecipients(Message.RecipientType.TO, to(messageHeader.getTo()));
            msg.addRecipients(Message.RecipientType.CC, cc(messageHeader.getCc()));
            msg.addRecipients(Message.RecipientType.BCC, bcc(messageHeader.getBcc()));
            msg.setSubject(super.encode(messageHeader.getSubject()));

            Multipart multipart = new MimeMultipart();

            if (StringUtil.isNotEmpty(messageBody.getContent())) {
                MimeBodyPart content = new MimeBodyPart();
                content.setContent(messageBody.getContent(),"text/html;charset=utf-8");
                multipart.addBodyPart(content);
            }

            if (CollectionUtil.isNotEmpty(messageBody.getFilepath())) {
                for (String filepath : messageBody.getFilepath()) {
                    MimeBodyPart file = new MimeBodyPart();
                    DataSource ds = new FileDataSource(filepath);
                    file.setDataHandler(new DataHandler(ds));
                    file.setFileName(super.encode(ds.getName()));
                    multipart.addBodyPart(file);
                }
            }

            if (CollectionUtil.isNotEmpty(messageBody.getFileBytes())) {
                for (Mail.Attachment attachment : messageBody.getFileBytes()) {
                    MimeBodyPart file = new MimeBodyPart();
                    DataSource ds = new ByteArrayDataSource(attachment.getAttachment(), attachment.getContentType());
                    file.setDataHandler(new DataHandler(ds));
                    file.setFileName(super.encode(attachment.getFilename()));
                    multipart.addBodyPart(file);
                }
            }

            msg.setContent(multipart);
            msg.setSentDate(new Date());

            return msg;
        } catch (MessagingException ex) {
            LOGGER.error("fail to build mail body", ex);
            throw new SendMailException("fail to build mail body");
        }
    }

    private Address[] to(List<String> toAddr) throws MessagingException {
        if (CollectionUtil.isEmpty(toAddr)) {
            throw new SendMailException("the receiver cannot be empty");
        }

        List<Address> to = new ArrayList<>(toAddr.size());
        for (String addr : toAddr) {
            to.add(new InternetAddress(addr));
        }

        return to.toArray(new Address[0]);
    }

    private Address[] cc(List<String> ccAddr) throws MessagingException {
        if (CollectionUtil.isEmpty(ccAddr)) {
            return null;
        }

        List<Address> cc = new ArrayList<>(ccAddr.size());
        for (String addr : ccAddr) {
            cc.add(new InternetAddress(addr));
        }

        return cc.toArray(new Address[0]);
    }

    private Address[] bcc(List<String> bccAddr) throws MessagingException {
        if (CollectionUtil.isEmpty(bccAddr)) {
            return null;
        }

        List<Address> bcc = new ArrayList<>(bccAddr.size());
        for (String addr : bccAddr) {
            bcc.add(new InternetAddress(addr));
        }

        return bcc.toArray(new Address[0]);
    }
}
