package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import system.model.User;
import system.service.UserService;

import java.util.List;

@Controller
//@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/list")
//    public @ResponseBody
//    List<User> getAllUsers() {
//        return userService.getAllUser();
//    }

    @GetMapping("/")
    public ModelAndView userList() {
        List<User> films = userService.getAllUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user-list");
        modelAndView.addObject("listUser", films);
        return modelAndView;
    }
}
