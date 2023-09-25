package com.juzi.email.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.juzi.email.utils.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.juzi.email.constants.EmailConstant.CAPTCHA_KEY;

/**
 * @author codejuzi
 */
@Slf4j
@RestController
@RequestMapping("/email")
public class EmailController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @GetMapping("/captcha")
    public Boolean getCaptcha(@RequestParam(value = "email") String emailAccount) {
        if (StrUtil.isBlank(emailAccount)) {
            log.error("邮箱为空!");
            return Boolean.FALSE;
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-za-z0-9.-]+$";
        if (!Pattern.matches(emailPattern, emailAccount)) {
            log.error("邮箱格式错误！");
            return Boolean.FALSE;
        }

        String captcha = RandomUtil.randomNumbers(6);

        try {
            EmailUtil.sendEmail(emailAccount, captcha);
            redisTemplate.opsForValue().set(CAPTCHA_KEY + emailAccount, captcha, 5, TimeUnit.MINUTES);
            return Boolean.TRUE;
        } catch (MessagingException e) {
            log.error("发送验证码失败！");
            throw new RuntimeException(e);
        }
    }
}
