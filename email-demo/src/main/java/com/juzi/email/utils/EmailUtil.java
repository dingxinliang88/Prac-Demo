package com.juzi.email.utils;

import com.juzi.email.config.EmailConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import static com.juzi.email.constants.EmailConstant.*;

/**
 * @author codejuzi
 */
@Slf4j
@Component
public class EmailUtil {

    private static JavaMailSender mailSender;

    private static EmailConfigProperties emailConfigProperties;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        EmailUtil.mailSender = mailSender;
    }


    @Autowired
    public void setEmailConfigProperties(EmailConfigProperties emailConfigProperties) {
        EmailUtil.emailConfigProperties = emailConfigProperties;
    }


    /**
     * 发送邮件
     *
     * @param emailAccount 邮箱
     * @param captcha      验证码
     */
    public static void sendEmail(String emailAccount, String captcha) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        // 组装邮件内容
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setSubject(EMAIL_SUBJECT);
        messageHelper.setText(buildEmailContent(EMAIL_HTML_CONTENT_PATH, captcha), true);
        messageHelper.setTo(emailAccount);
        messageHelper.setFrom(EMAIL_CN_TITLE + "<" + emailConfigProperties.getFrom() + ">");
        mailSender.send(message);
    }

    @SuppressWarnings("SameParameterValue")
    private static String buildEmailContent(String emailHtmlPath, String captcha) {
        // 加载邮件模板
        ClassPathResource resource = new ClassPathResource(emailHtmlPath);

        StringBuilder buffer = new StringBuilder();
        String line;
        InputStream in = null;
        BufferedReader fileReader = null;
        try {
            in = resource.getInputStream();
            fileReader = new BufferedReader(new InputStreamReader(in));

            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            log.error("读取邮件模板失败" + e.getMessage());
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return MessageFormat.format(buffer.toString(), captcha,
                EMAIL_CN_TITLE, EMAIL_EN_TITLE, PLATFORM_RESPONSIBLE_PERSON, PLATFORM_ADDR);
    }
}
