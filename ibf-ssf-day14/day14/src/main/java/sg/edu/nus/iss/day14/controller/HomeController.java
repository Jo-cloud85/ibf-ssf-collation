package sg.edu.nus.iss.day14.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/pagea")
    public String pageA() {
        return "pagea";
    }

    @PostMapping("/pagea")
    public String postPageA(
        @RequestBody MultiValueMap<String, String> form, 
        HttpSession httpSession,
        Model model) {

        String fullname = form.getFirst("fullname");
        httpSession.setAttribute("fullname", fullname);

        // add attribute from http session, not from the form
        model.addAttribute("fullname", (String) httpSession.getAttribute("fullname")); 

        // go to pageB.html
        return "pageb";
    }

    @GetMapping("/pagec")
    public String navigateToPageC(Model model, HttpSession httpSession) {
        String fullname = (String) httpSession.getAttribute("fullname");
        model.addAttribute("fullname", fullname);
        return "pagec";
    }
    
    @GetMapping("/page0")
    public String redirectBackToPageA(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/home/pagea";
    }
}
