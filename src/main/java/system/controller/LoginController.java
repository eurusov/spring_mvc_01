package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import system.model.User;
import system.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView doLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLoginPost(@ModelAttribute("user") User user, HttpSession httpSession) {
        User authUser = userService.getUser(user.getEmail(), user.getPassword());
        if (authUser == null) {
            return "redirect:/login";
        } else {
            httpSession.setAttribute("sessionUser", authUser);
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String doLogout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/login";
    }
}
