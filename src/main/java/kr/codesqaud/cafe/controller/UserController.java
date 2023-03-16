package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;

    @Autowired //의존성 주입
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/form")
    public String createUserForm() {
        return "user/form";
    }

    @PostMapping("/user/form")
    public String join(UserForm form) {
        User user = new User(form.getName(), form.getEmail(), form.getPassword());

        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{name}")
    public String usersProfile(Model model, @PathVariable String name) {
        model.addAttribute("userProfile", userRepository.findByName(name).orElse(null));

        return "user/profile";
    }
}