package org.example.controller;

import org.example.entity.User;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    RoleService roleService;

//Чтобы что-то добавить или получить со страницы мы обращаемся к model. В GET запросе на страницу добавляется новый пустой объект класса User.
// Это сделано для того, чтобы при POST запросе не доставать данные из формы регистрации по одному (username, password, passwordComfirm),
// а сразу получить заполненный объект userForm.
    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new User());
        return "registration";
    }

//Метод addUser() в качестве параметров ожидает объект пользователя (userForm), который был добавлен при GET запросе.
// Аннотация Valid проверяет выполняются ли ограничения, установленные на поля, в данном случае длина не меньше 2 символов.
// Если ограничения не были выполнены, то bindingResult будет содержать ошибки.
//Если пароль и его подтверждение не совпадают добавляем сообщение на страницу и возвращаем её.
// В конце пробуем сохранить добавить пользователя в БД.
    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "registration";
        }
        if(!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if(!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        return "redirect:/";
    }

    @GetMapping("/")
    public String createRole(){
        if(roleService.getById(1L) == null){
            roleService.save(1L, "ROLE_USER");
            roleService.save(2L, "ROLE_ADMIN");
        }
        return "index";
    }
}
