package com.henlf.mail.domain;

import lombok.*;

import java.util.List;

/**
 * 邮件对象
 * @author tanghongfeng
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    private MailMessageHeader mailMessageHeader;
    private MailMessageBody mailMessageBody;

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailMessageHeader {
        /**
         * 发件人邮箱地址
         */
        private String from;
        /**
         * 接收者
         */
        private List<String> to;
        /**
         * 抄送
         */
        private List<String> cc;
        /**
         * 密送
         */
        private List<String> bcc;
        /**
         * 邮件主题
         */
        private String subject;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailMessageBody {
        /**
         * 文本内容
         */
        private String content;
        /**
         * 附件文件路径
         */
        private List<String> filepath;
        /**
         * 附件文件字节
         */
        private List<Attachment> fileBytes;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Attachment {
        /**
         * 文件字节数组
         */
        private byte[] attachment;
        /**
         * 文件 Mime Type
         */
        private String contentType;
        /**
         * 附件文件名
         */
        private String filename;
    }
}
