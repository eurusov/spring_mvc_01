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
    public ModelAndView doLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView doLoginPost(@ModelAttribute("authUser") User authUser, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUser(authUser.getEmail(), authUser.getPassword());
        if (user == null || user.getId() == null) {
            authUser.setId(null);
            authUser.setEmail(null);
            authUser.setPassword(null);
            authUser.setFirstName(null);
            authUser.setLastName(null);
            authUser.setCountry(null);
            authUser.setRole(null);
            modelAndView.setViewName("login");
        } else {
            httpSession.setAttribute("authUser", user);
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView doLogout(HttpSession httpSession) {
        httpSession.removeAttribute("authUser");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
