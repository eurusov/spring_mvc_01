package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/new")
    public ModelAndView addUser() {
        User user = new User();
        user.setRole("user");
        System.out.println(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edit-form");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editUser(@PathVariable("id") long id) {
        User user = userService.getUser(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edit-form");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editUser(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(user);
        if (user.getId() == null) {
            userService.addUser(user);
        } else {
            userService.updateUser(user);
        }
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
