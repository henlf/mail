> 对 Java 发送邮件的一个封装，提供普通邮件以及带附件的邮件发送。
> 提供核心 jar 包，不依赖具体平台，简单易用，可以参考单元测试。另外也提供了 starter 包。
> 其使用方式：

添加依赖：
```java
<dependency>
    <groupId>com.dxx</groupId>
    <artifactId>mail-dxx-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```
```java
application.properties

flex.mail.host=smtp.xxxx.com
flex.mail.account=xxx@xxx.com
flex.mail.password=xxxx


@Autowired
private DefaultSender defaultSender;
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
```