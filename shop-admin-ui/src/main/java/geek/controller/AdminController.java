package geek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @RequestMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("activePage", "None");
        return "index";
    }
}
