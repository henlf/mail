package com.henlf.mail;

import com.henlf.mail.callback.MailCallback;
import com.henlf.mail.config.MailProperties;
import com.henlf.mail.domain.Mail;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class DefaultSenderTest {
    private AbstractSender sender;

    @Before
    public void beforeTest() {
        /*MailProperties mailProperties = MailProperties.builder()
                                            .auth(true)
                                            .host("")
                                            .account("")
                                            .password("PHHSMSAFITPIGIBO")
                                            .protocol("smtp")
                                            .port(25)
                                            .ssl(true)
                                            .build();*/
        MailProperties mailProperties = MailProperties.builder()
                .auth(true)
                .host("") // smtp 服务器
                .account("") // 账户
                .password("") // TODO 账户密码
                .protocol("smtp")
                .port(25)
                .ssl(false)
                .build();
        sender = new DefaultSender(mailProperties);
    }

    @Test
    public void test_sendPlainMailSync() {
        Mail mail = Mail.builder()
                .mailMessageBody(Mail.MailMessageBody.builder()
                        .content("test")
                        .build())
                .mailMessageHeader(Mail.MailMessageHeader.builder()
                        .from("") // 发送者
                        .to(Arrays.asList("")) // 接收者
                        .cc(Arrays.asList("")) // 抄送
                        .subject("测试文本邮件发送")
                        .build())
                .build();
        sender.sendMail(mail);
    }

    @Test
    public void test_sendPlainMailAsync() {
        Mail mail = Mail.builder()
                .mailMessageBody(Mail.MailMessageBody.builder()
                        .content("test")
                        .build())
                .mailMessageHeader(Mail.MailMessageHeader.builder()
                        .from("xxxx@163.com")
                        .to(Arrays.asList("xxx"))
                        .cc(Arrays.asList("xxxx"))
                        .subject("测试文本邮件发送")
                        .build())
                .build();
        sender.sendMailAsync(mail, new MailCallback() {
            @Override
            public void onSuccess() {
                System.out.println("发送邮件成功");
            }

            @Override
            public void onError(Throwable cause) {
                System.out.println(cause);
            }
        });
    }

    @Test
    public void test_sendMultiMailSync() {
        Mail mail = Mail.builder()
                .mailMessageBody(Mail.MailMessageBody.builder()
                        .content("test 附件")
                        .filepath(Arrays.asList("/Users/flex/Desktop/xxx.docx"))
                        .build())
                .mailMessageHeader(Mail.MailMessageHeader.builder()
                        .from("")
                        .to(Arrays.asList(""))
                        .cc(Arrays.asList(""))
                        .subject("测试文本邮件发送")
                        .build())
                .build();
        sender.sendMail(mail);
    }
}
