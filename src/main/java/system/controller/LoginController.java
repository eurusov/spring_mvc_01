package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import system.model.User;
import system.service.UserService;

@Controller
@SessionAttributes("authUser")
public class LoginController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("authUser")
    public User setUpUserForm() {
        return new User();
    }


    @GetMapping("/login")
    public ModelAndView doLogin(@ModelAttribute("authUser") User authUser) {
        System.out.println("authUser: " + authUser);
        ModelAndView modelAndView = new ModelAndView();
        if (authUser == null || authUser.getId() == null) {
            modelAndView.setViewName("login");
        } else {
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView doLoginPost(@ModelAttribute("authUser") User authUser) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUser(authUser.getEmail(), authUser.getPassword());
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            authUser.setId(null);
            authUser.setEmail(null);
            authUser.setPassword(null);
            authUser.setFirstName(null);
            authUser.setLastName(null);
            authUser.setCountry(null);
            authUser.setRole(null);
            modelAndView.setViewName("login");
        } else {
            authUser.setId(user.getId());
            authUser.setEmail(user.getEmail());
            authUser.setPassword(user.getPassword());
            authUser.setFirstName(user.getFirstName());
            authUser.setLastName(user.getLastName());
            authUser.setCountry(user.getCountry());
            authUser.setRole(user.getRole());
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView doLogout(@ModelAttribute("authUser") User authUser) {
        ModelAndView modelAndView = new ModelAndView();
        authUser.setId(null);
        authUser.setEmail(null);
        authUser.setPassword(null);
        authUser.setFirstName(null);
        authUser.setLastName(null);
        authUser.setCountry(null);
        authUser.setRole(null);
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
