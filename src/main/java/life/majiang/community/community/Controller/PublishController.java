package life.majiang.community.community.Controller;

import life.majiang.community.community.Model.Question;
import life.majiang.community.community.Model.User;
import life.majiang.community.community.mapper.QuestionMapper;
import life.majiang.community.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
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
        User user = null;
        //首先查询数据库中是否有这一token的user
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                //查询数据库中是否有这一token的user
                user = userMapper.findByToken(token);
                //如果数据库中存在这一user
                if(user!=null){
                    request.getSession().setAttribute("githubUser", user);
                }
                break;
            }
        }
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
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        System.out.println(question.toString());
        questionMapper.create(question);

        model.addAttribute("error","发布成功");

        return "publish";
    }
}
