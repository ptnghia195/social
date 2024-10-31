package new_butter.new_butter.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")  // Nhận tin nhắn từ /app/chat
    @SendTo("/topic/messages") // Gửi đến tất cả các client đã đăng ký kênh /topic/messages
    public String sendMessage(String message) {
        return message; // Trả về tin nhắn cho tất cả client
    }
}