package com.gtja.pb.comb.config;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppRequestMapping {

  @GetMapping("/myInfo")
  public Map<String, Object> home() {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("id", UUID.randomUUID().toString());
    model.put("content", "Hello comb");
    return model;
  }

  @GetMapping("/user")
  public Principal user(Principal user) {
    return user;
  }

  @GetMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }



}
