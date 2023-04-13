package gymhum.de;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CaesarController {

    @GetMapping("/caesar")
    public String caesar(@RequestParam(name="activePage", required = false, defaultValue = "caesar") String activePage, Model model){
        model.addAttribute("activePage", "caesar");
        return "index.html";
    }
}
