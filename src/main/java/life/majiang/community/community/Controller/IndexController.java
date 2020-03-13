package life.majiang.community.community.Controller;

import life.majiang.community.community.Service.QuestionService;
import life.majiang.community.community.dto.PaginationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    // "/"表示根目录，什么东西都不输入的时候,访问这个目录
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name="page", defaultValue = "1")Integer page,
                        @RequestParam(name="size", defaultValue = "2")Integer size){

        //获取当前页面的所有信息。
        PaginationDTO pagination = questionService.GetList(page, size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
