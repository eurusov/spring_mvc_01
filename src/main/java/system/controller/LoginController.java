package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/login")
//    public ModelAndView doLogin(@SessionAttribute("authUser") User authUser) {
    public ModelAndView doLogin(@ModelAttribute("authUser") User authUser) {
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("authUser", authUser);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public String doLoginPost(@ModelAttribute("authUser") User authUser, HttpSession httpSession) {
        User user = userService.getUser(authUser.getEmail(), authUser.getPassword());
        if (user == null) {
            return ("redirect:/login");
        } else {
            httpSession.setAttribute("authUser", user);
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String doLogout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/login";
    }
}
