package com.example.liskeuzetool.web;

import com.example.liskeuzetool.model.Advice;
import com.example.liskeuzetool.repo.AdviceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"${app.cors.allowed-origins:*}"})
public class AdviceController {

  private final JavaMailSender mailSender;
  private final AdviceRepository repo;

  @Value("${app.mail.from}")
  private String mailFrom;

  @Value("${app.mail.to.rob}")
  private String mailToRob;

  public AdviceController(JavaMailSender mailSender, AdviceRepository repo) {
    this.mailSender = mailSender;
    this.repo = repo;
  }

  @PostMapping("/send-lis-advice")
  public ResponseEntity<?> sendToRob(@RequestBody Map<String,Object> body) {
    Advice a = mapToAdvice(body);
    repo.save(a);

    String subject = "Nieuw LiS-advies – " + (a.getName() != null ? a.getName() : "Onbekend");
    String text = (String) body.getOrDefault("advicePlain", "Geen tekst");
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom(mailFrom);
    msg.setTo(mailToRob);
    msg.setSubject(subject);
    msg.setText(text);
    mailSender.send(msg);

    return ResponseEntity.ok(java.util.Map.of("ok", true));
  }

  @PostMapping("/send-lis-advice-user")
  public ResponseEntity<?> sendToUser(@RequestBody Map<String,Object> body) {
    String to = (String) body.get("to");
    String subject = (String) body.get("subject");
    String text = (String) body.getOrDefault("text", "");
    if (to == null || subject == null || (text == null || text.isEmpty())) {
      return ResponseEntity.badRequest().body(java.util.Map.of("ok", false, "error", "Missing to/subject/body"));
    }
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom(mailFrom);
    msg.setTo(to);
    msg.setSubject(subject);
    msg.setText(text);
    mailSender.send(msg);
    return ResponseEntity.ok(java.util.Map.of("ok", true));
  }

  private Advice mapToAdvice(Map<String,Object> body) {
    Advice a = new Advice();
    a.setName((String) body.getOrDefault("name",""));
    a.setEmail((String) body.getOrDefault("email",""));
    a.setBackground((String) body.getOrDefault("background",""));
    a.setRole((String) body.getOrDefault("role",""));
    a.setAdviceDate((String) body.getOrDefault("adviceDate",""));
    a.setAdvicePlain((String) body.getOrDefault("advicePlain",""));
    Object wants = body.get("wantsContact");
    a.setWantsContact(wants instanceof Boolean ? (Boolean) wants : null);
    try {
      com.fasterxml.jackson.databind.ObjectMapper om = new com.fasterxml.jackson.databind.ObjectMapper();
      a.setModulesJson(om.writeValueAsString(body.get("modules")));
      java.util.List<String> interests = (java.util.List<String>) body.get("interests");
      a.setInterests(interests);
    } catch (Exception ignored) {}
    return a;
  }
}
