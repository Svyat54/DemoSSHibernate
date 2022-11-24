package org.example.controller;

import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String userList(Model model){
        model.addAttribute("allUsers", userService.allUser());
        return "admin";
    }
//Метод deleteUser() использует аннотацию RequestParam т.е. в представлении будет форма, которая должная передать два параметра – userId и action.
// Ссылка будет иметь вид http://localhost:8080/admin?userId=24&action=delete при выполнении такого запроса будет удален пользователь с id=24.
    @PostMapping("/admin")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action, Model model){
        if(action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }
//Еще один вариант передать параметры в URL – использовать PathVariable. С помощью этой аннотации мы получаем отдельные части URL,
// для метод getUser() URL будет выглядеть следующим образом: http://localhost:8080/admin/gt/24, после перехода выведется
// список всех пользователей с id>24.
    @GetMapping("/admin/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model){
        model.addAttribute("allUsers", userService.userGtList(userId));
        return "admin";
    }
}
