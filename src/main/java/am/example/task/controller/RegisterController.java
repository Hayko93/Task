package am.example.task.controller;

import am.example.task.entity.UserEntity;
import am.example.task.service.impl.UserServiceImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class RegisterController {

  private final UserServiceImpl userService;

  public RegisterController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @GetMapping("/register")
  public ModelAndView registration() {
    ModelAndView modelAndView = new ModelAndView();
    UserEntity user = new UserEntity();
    modelAndView.addObject("userEntity", user);
    modelAndView.setViewName("register");
    return modelAndView;
  }

  @PostMapping("/register")
  public ModelAndView createNewUser(@Valid @RequestBody UserEntity user, BindingResult bindingResult) {
    ModelAndView modelAndView = new ModelAndView();
    if (userService.existEmail(user.getEmail())) {
      bindingResult
          .rejectValue("email", "error.userEntity",
              "There is already a user registered with the email provided");
    }
    if (!bindingResult.hasErrors()) {
      userService.add(user);
      modelAndView.addObject("successMessage", "Der friend, pleas your visit email and confirm");
    }
    modelAndView.setViewName("login");
    return modelAndView;
  }

  @GetMapping("/activate/{code}")
  public ModelAndView activate(@PathVariable String code){
    ModelAndView modelAndView = new ModelAndView();
    boolean isActivate = userService.activateUser(code);
    if (isActivate){
      modelAndView.addObject("registerMessage","User activated");
    }else {
      modelAndView.addObject("registerMessage", "activation cod is not found");
    }
    modelAndView.setViewName("login");
    return modelAndView;
  }
}
