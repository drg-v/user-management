package user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import user.entities.User;
import user.services.UserService;

@Controller
public class UserController {
	
    @Autowired
    UserService userService;
    
    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAll());
		return "index";
    }
    
    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }
    
    @PostMapping("/registration")
    public String submitRegistration(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/login";
    }
    
    @PostMapping(value="/change", params={"delete"})
    public String delete(@RequestParam("id")List<Integer> ids, Authentication authentication) {
        if(userService.checkAuthentication(authentication, ids)) {
            authentication.setAuthenticated(false);
        }
        userService.delete(ids);
        return "redirect:/";
    }
    
    @PostMapping(value="/change", params={"block"})
    public String block(@RequestParam("id")List<Integer> ids, Authentication authentication) {
        if(userService.checkAuthentication(authentication, ids)) {
            authentication.setAuthenticated(false);
        }
        userService.block(ids);
        return "redirect:/";
    }
    
    @PostMapping(value="/change", params={"unlock"})
    public String unlock(@RequestParam("id")List<Integer> ids) {
        userService.unlock(ids);
        return "redirect:/";
    }
}
