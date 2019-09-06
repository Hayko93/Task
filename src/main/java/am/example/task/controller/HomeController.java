package am.example.task.controller;

import am.example.task.entity.BoardEntity;
import am.example.task.entity.BoardMemberEntity;
import am.example.task.entity.UserEntity;
import am.example.task.service.AdminService;
import am.example.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final AdminService adminService;

    @Autowired
    public HomeController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

   @PostMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByEmail(auth.getName());
        if (user == null || user.getPassword() == null){
            modelAndView.addObject("error","Email or Password invalid, please verify");
            modelAndView.setViewName("login");
        }else {
            modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
            modelAndView.addObject("usersList", userService.getAll());
            modelAndView.addObject("boarderMember", user.getBoardMembersEntities());
            if (user.getRoles().contains("ADMIN")){
                modelAndView.addObject("boards", user.getBoard());
            }
            modelAndView.setViewName("home");
        }
        return modelAndView;
    }

    @PostMapping("/add/board")
    public ModelAndView createBoard(@Valid @RequestParam(name = "admin") UserEntity admin, @RequestParam(name = "title") String title){
        BoardEntity board = new BoardEntity(title, admin);
        adminService.addBoard(board);
        return new ModelAndView("home");
    }

    @PostMapping("/admin/add/user")
    public ModelAndView addUserInBoard(@RequestParam(name = "user") UserEntity user, @RequestParam(name = "board") BoardEntity boardEntity){
        BoardMemberEntity boardMember = new BoardMemberEntity(user,boardEntity);
        adminService.addBoardMember(boardMember);
        return new ModelAndView("home");
    }

    @DeleteMapping("/admin/delete/user/{bMId}")
    public ModelAndView deleteUserInBoard(@PathVariable("bMId") Integer boardMemberId){
        adminService.deleteBoardMember(boardMemberId);
        return new ModelAndView("home");
    }
}
