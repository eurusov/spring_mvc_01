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
    public String mainEntryPoint(HttpSession httpSession) {
        User authUser = (User) httpSession.getAttribute("authUser");

        if (authUser == null) {
            authUser = new User();
            httpSession.setAttribute("authUser", authUser);
        }
        if (authUser.getId() == null) {
            return "redirect:/login";
        } else if (authUser.getRole().equals("admin")) {
            return "redirect:/list";
        } else {
            return "redirect:/info?id=" + authUser.getId();
        }
    }

    @GetMapping("/new")
    public ModelAndView addUser() {
        User user = new User();
        user.setRole("user");
        System.out.println(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edit-and-new");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/new")
    public String addNewUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/";
    }


    @GetMapping("/edit")
    public ModelAndView editUser(@RequestParam("id") long id) {
        User user = userService.getUser(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edit-and-new");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

    @GetMapping("/list")
    public ModelAndView showUserList() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> users = userService.getAllUsers();
        modelAndView.setViewName("user-list");
        modelAndView.addObject("listUser", users);
        return modelAndView;
    }

    @GetMapping("/info")
    public ModelAndView showUserInfo(@RequestParam long id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUser(id);
        modelAndView.addObject("authUser", user);
        modelAndView.setViewName("user-info");
        return modelAndView;
    }
}
