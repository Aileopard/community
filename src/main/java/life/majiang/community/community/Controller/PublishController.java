package life.majiang.community.community.Controller;

import life.majiang.community.community.Model.Question;
import life.majiang.community.community.Model.User;
import life.majiang.community.community.Service.QuestionService;
import life.majiang.community.community.dto.QuestionDTO;
import life.majiang.community.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("editId")Integer editId,
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tags")String tags,
            HttpServletRequest request,
            Model model){
        //为了在提交以后还会显示。
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tags",tags);

        if(title == null || title ==""){
            model.addAttribute("error", "问题标题不能为空");
            return "publish";
        }
        if(description == null || description ==""){
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if(tags == null || tags ==""){
            model.addAttribute("error", "标签不为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("githubUser");
        //如果没有，则返回用户未登录
        if(user == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        //如果有该用户，则将他的评论写如数据库中
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTags(tags);
        question.setCreator(user.getId());
        System.out.println(editId);
        questionService.createOrUpdate(editId,question);

        model.addAttribute("error","发布成功");

        //发布成功以后直接跳转到index，记得要重定向。
        return "redirect:/";
    }


    @GetMapping("/publish/{id}")
    public String edit(@PathVariable Integer id,
                       Model model){
        // 点击编辑后，通过id获取问题信息
        QuestionDTO questionDTO = questionService.getById(id);
        System.out.println(questionDTO.toString());
        // 将问题的title、description、tags都传入publish.html
        model.addAttribute("editId",id); // 用于传递数据
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tags",questionDTO.getTags());
        return "publish";
    }

}
