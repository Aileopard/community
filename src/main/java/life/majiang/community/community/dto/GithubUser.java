package life.majiang.community.community.dto;

import lombok.Data;

//GithubUser是封装GitHub的一个返回值，里面包含用户信息
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}
