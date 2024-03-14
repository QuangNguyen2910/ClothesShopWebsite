package com.example.project_economic.controller;


import com.example.project_economic.entity.UserEntity;
import com.example.project_economic.service.HistoryCardService;
import com.example.project_economic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api/history-page")
public class HistoryFormController {

    private final HistoryCardService historyCardService;
    private final UserService userService;

    public HistoryFormController(HistoryCardService historyCardService, UserService userService) {
        this.historyCardService = historyCardService;
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public String showPageHistory(Model model, @PathVariable Long userId){
        model.addAttribute("history_card",this.historyCardService.findByUserId(userId));
        model.addAttribute("user",this.userService.findUserById(userId));
        model.addAttribute("users", new UserEntity());
        return "home/my-account";
    }
}
