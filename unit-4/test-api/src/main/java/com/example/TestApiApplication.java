package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@RestController
@RequestMapping("/message")
@SpringBootApplication
public class TestApiApplication {

	private final MessageRepository messageRepository;

	@Autowired
	public TestApiApplication(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Message> getAllMessages() {
		return messageRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Message createMessage(@RequestBody Message message) {
        // Optionally modify the message
        message.setMessage("Hello, " + message.getMessage());
        return messageRepository.save(message);
    }

	public static void main(String[] args) {
		SpringApplication.run(TestApiApplication.class, args);
	}

}
