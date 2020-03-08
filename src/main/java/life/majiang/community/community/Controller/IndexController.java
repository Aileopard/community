package life.majiang.community.community.Controller;

import life.majiang.community.community.Model.User;
import life.majiang.community.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    // "/"表示根目录，什么东西都不输入的时候,访问这个目录
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //查询数据库中是否有这一token的user
                    User user = userMapper.findByToken(token);
                    //如果数据库中存在这一user
                    if(user!=null){
                        request.getSession().setAttribute("githubUser", user);
                    }
                    break;
                }
        }

        return "index";
    }
}
