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

    /**
     * Redirects user according to one of three options:
     * 1) if the user is not logged in - redirects him to the login page;
     * 2) if  the  user is logged  in with admin rights - redirects him to the /list URL;
     * 3) if the user is logged in without admin rights - redirects him to his /info URL.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainEntryPoint(HttpSession httpSession) {
        User sessionUser = (User) httpSession.getAttribute("sessionUser");

        System.out.println("[GET] | mainEntryPoint(...) | sessionUser = " + sessionUser);

        if (sessionUser == null || sessionUser.getId() == null) {
            return "redirect:/login";
        } else if (sessionUser.getRole().equals("admin")) {
            return "redirect:/list";
        } else {
            return "redirect:/info?id=" + sessionUser.getId();
        }
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView addUser() {
        User user = new User();
        user.setRole("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("edit-and-new");
        return modelAndView;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNewUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editUser(@RequestParam("id") long id) {
        User user = userService.getUser(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("edit-and-new");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("user") User user, HttpSession httpSession) {
        userService.updateUser(user);
        /* if the user updated his own data, then we need to update the session user */
        User sessionUser = (User) httpSession.getAttribute("sessionUser");
        if (sessionUser.getId().equals(user.getId())) {
            httpSession.setAttribute("sessionUser", user);
        }
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        System.out.println("[GET] | deleteUser(...) | id = " + id);
        boolean deleted = userService.deleteUser(id);
        System.out.println("deleted = " + deleted);
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
        User user = userService.getUser(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user-info");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
