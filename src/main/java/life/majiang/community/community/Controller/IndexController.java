package life.majiang.community.community.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    // "/"表示根目录，什么东西都不输入的时候,访问这个目录
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
