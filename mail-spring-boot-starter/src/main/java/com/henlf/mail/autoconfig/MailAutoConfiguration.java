package com.henlf.mail.autoconfig;

import com.henlf.mail.DefaultSender;
import com.henlf.mail.config.MailProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author flex
 */
@ConditionalOnMissingBean(DefaultSender.class)
@EnableConfigurationProperties(MailConfigProperties.class)
@Configuration
public class MailAutoConfiguration {
    private MailConfigProperties mailConfigProperties;

    @Autowired
    public MailAutoConfiguration(MailConfigProperties mailConfigProperties) {
        this.mailConfigProperties = mailConfigProperties;
    }

    @Bean
    public DefaultSender defaultSender() {
        MailProperties mailProperties = new MailProperties();
        BeanUtils.copyProperties(mailConfigProperties, mailProperties);
        return new DefaultSender(mailProperties);
    }
}
