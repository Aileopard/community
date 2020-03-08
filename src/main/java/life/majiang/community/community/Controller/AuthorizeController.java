package life.majiang.community.community.Controller;

import life.majiang.community.community.Model.User;
import life.majiang.community.community.dto.AccessTokenDTO;
import life.majiang.community.community.dto.GithubUser;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

//进行授权操作
@Controller
public class AuthorizeController {

    //自动从spring容器中的实例化的实例加载到我当前使用的上下文
    //这个用于将code和access_token交换，然后将其发送到GitHub上，来获取
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;

    //通过spring的一个value注解，@value会去配置文件中寻找到的值赋给clientID
    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    //这个是从网址uri中获取到
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        // 从之前创建好的APP下得到这些信息
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        //将交换过的访问令牌发送到GitHub中去。
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //通过access_token得到用户信息
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser != null && githubUser.getId()!=null){
           //登录成功，写cookie和session
           User user = new User();
           String token = UUID.randomUUID().toString();
           user.setToken(token);
           user.setName(githubUser.getName());
           user.setAccountID(String.valueOf(githubUser.getId()));
           //设置为当前时间
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
           //如果你不去创建一个cookie，它会自动给你一个
            //将token写入到cookie中去
            response.addCookie(new Cookie("token", token));
           //重定向到index页面。

        }else{
           // 登录失败，重新登录。
        }
        return "redirect:/";
    }
}
