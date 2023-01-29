package com.Travel_app.controller;

import com.Travel_app.db.model.User;
import com.Travel_app.db.model.Utility;
import com.Travel_app.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Controller
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserService userService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(){
        return "ForgotPassword";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(Model model, HttpServletRequest request){
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        if(userService.findByEmail(email)){
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            try {
                sendEmail(email, resetPasswordLink);
                model.addAttribute("message", "Link do odzyskania hasła został wysłany na podany adres email.");
            }
            catch(MessagingException | UnsupportedEncodingException exception){
                model.addAttribute("error", "Wystąpił błąd podczas próby wysłania linku");
            }
        }
        else{
            model.addAttribute("error", "Użytkownik z podanym adresem mailowym nie istnieje. Sprawdź adres email i spróbuj ponownie.");
        }
        return "ForgotPassword";
    }

    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("","Pomoc techniczna");
        helper.setTo(recipientEmail);

        String subject = "Link do odzyskania hasła";

        String content = "<p>Witaj, </p>"
                + "<p>Kliknij poniższy link, aby zmienić hasło do swojego konta: </p>"
                + "<p><a href=\"" + link + "\">Zmiania hasła</a></p>"
                + "<br>"
                + "Jeżeli nie wybrałeś opcji przypomnienia hasła w naszym serwisie, uprzejmie prosimy o zignorowanie tej wiadomości email. </p>"
                + "<br>"
                + "Serdecznie dziękujemy za odwiedzenie naszej strony!";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);

    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value="token") String token, Model model){
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);
        if(user == null){
            model.addAttribute("message", "Podano niepoprawny link");
            return "message";
        }
        return "ResetPassword";
    }

    @PostMapping("/reset_password")
    public String processResetPasswordForm(Model model, HttpServletRequest request){
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Odzyskiwanie hasła");

        if(user == null){
            model.addAttribute("message", "Niepoprawny link odzyskiwania hasła");
            return "message";
        }
        else{
            userService.updatePassword(user, password);
            model.addAttribute("message", "Udana zmiana hasła.");
        }
        return "message";
    }
}
