package com.example.fruitStore.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from:no-reply@fruitstore.com}")
    private String fromEmail;

    @Value("${app.mail.app-name:Fruit Store}")
    private String appName;

    @Override
    public void sendPasswordResetEmail(String toEmail, String resetLink) {

        String subject = "Reset your password - " + appName;

        String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Reset Password</title>
                </head>
                <body style="margin:0; padding:0; background-color:#f6f8fb; font-family:Arial, sans-serif;">
                
                    <div style="max-width:600px; margin:30px auto; background:#ffffff; border-radius:12px; overflow:hidden; box-shadow:0 6px 18px rgba(0,0,0,0.06);">
                        
                        <!-- Header -->
                        <div style="padding:20px 24px; background:#111827; color:#ffffff;">
                            <h2 style="margin:0; font-size:18px;">%s</h2>
                        </div>
                        
                        <!-- Body -->
                        <div style="padding:24px;">
                            <h3 style="margin:0 0 10px; color:#111827; font-size:20px;">Reset your password</h3>
                            <p style="margin:0 0 14px; color:#374151; font-size:14px; line-height:1.6;">
                                We received a request to reset your password. Click the button below to set a new password.
                            </p>
                
                            <div style="margin:22px 0;">
                                <a href="%s"
                                   style="background:#2563eb; color:#ffffff; padding:12px 18px; text-decoration:none;
                                          border-radius:8px; font-size:14px; display:inline-block;">
                                    Reset Password
                                </a>
                            </div>
                
                            <p style="margin:0 0 14px; color:#6b7280; font-size:13px; line-height:1.6;">
                                This link will expire in <b>15 minutes</b>.
                            </p>
                
                            <p style="margin:0; color:#6b7280; font-size:13px; line-height:1.6;">
                                If the button doesn’t work, copy and paste this link into your browser:
                            </p>
                
                            <p style="word-break:break-all; margin:10px 0 0; font-size:13px;">
                                <a href="%s" style="color:#2563eb;">%s</a>
                            </p>
                        </div>
                
                        <!-- Footer -->
                        <div style="padding:16px 24px; background:#f9fafb; color:#6b7280; font-size:12px;">
                            If you didn’t request this, you can safely ignore this email.
                            <br/>
                            © %s
                        </div>
                    </div>
                
                </body>
                </html>
                """.formatted(appName, resetLink, resetLink, resetLink, appName);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(html, true); // ✅ true = HTML

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send reset email");
        }
    }
}
