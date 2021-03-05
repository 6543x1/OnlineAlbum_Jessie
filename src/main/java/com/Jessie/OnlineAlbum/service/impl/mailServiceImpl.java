package com.Jessie.OnlineAlbum.service.impl;

import com.Jessie.OnlineAlbum.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;

@Component
@Service("mailService")
public class mailServiceImpl implements MailService
{
    @Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;

    @Override
    public void sendResetPw(String dest, String theInfo)
    {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(dest);//接收人
        msg.setText(theInfo);  //这里的邮件内容是 文本类型
        // msg.setCc(cc);// 抄送
        // msg.setBcc(bcc);// 密送
        // msg.setReplyTo(replyTo);// 回复
        // msg.setSentDate(new Date());// 发送时间
        // msg.setSubject(subject);// 主题
        // msg.setFrom(from);// 发送人
        try
        {
            this.mailSender.send(msg);
        } catch (MailException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    public static String getRandomString()
    {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++)
        {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum))
            {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum))
            { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}
