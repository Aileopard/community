package life.majiang.community.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.community.dto.AccessTokenDTO;
import life.majiang.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * GithubProvider获取将之前GitHub返回的code
 * 然后社区网站将会通过access_token API来将access_token发送给GitHub
 */
//与controller相似，不过controller是把当前的类作为路由API的一个承载者
//而component仅仅把当前的类初始化到spring容器的上下文
@Component
public class GithubProvider {

    //将交换后的access_token发送到GitHub上
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        //此处的json就是access_token（访问令牌）
        //toJSONString将
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        //url就是GitHub所提供的地址
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        // 上面是发送给GitHub，下面是获取GitHub的回应（回应一个新的形式的access_token和token_type）。
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.substring(13, 53);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //使用访问令牌访问API后，获取GitHub返回的一个用户信息
    public GithubUser getUser(String accseeToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accseeToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //自动转换成一个Java的类对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
