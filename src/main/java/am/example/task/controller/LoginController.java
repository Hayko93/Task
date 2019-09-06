package am.example.task.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping({"/","/login"})
public class LoginController {

  @GetMapping
  public ModelAndView getLogin() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("login");
    return modelAndView;
  }



}
