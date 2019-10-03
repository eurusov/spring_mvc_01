package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import system.model.User;
import system.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
//@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView mainEntryPoint(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();

        User authUser = (User) httpSession.getAttribute("authUser");

        System.out.println("From mainEntryPoint(), authUser = " + authUser);

        // Non-authenticated user
        if (authUser == null) {
            authUser = new User();
            httpSession.setAttribute("authUser", authUser);
        }
        // Non-authorized user
        if (authUser.getId() == null) {
            modelAndView.addObject("authUser", authUser);
            modelAndView.setViewName("login");
        } else if (authUser.getRole().equals("admin")) {  // Authorize user
            List<User> users = userService.getAllUser();
            modelAndView.setViewName("user-list");
            modelAndView.addObject("listUser", users);
        } else {
            modelAndView.setViewName("user-page");
        }

        System.out.println("From mainEntryPoint(), authUser = " + authUser);

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

    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
