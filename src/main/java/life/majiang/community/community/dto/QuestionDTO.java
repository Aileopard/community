package life.majiang.community.community.dto;

import life.majiang.community.community.Model.User;
import lombok.Data;

// 比Question多了一个user，通过user来获取用户的头像。
@Data
public class QuestionDTO {
    private Integer id; //问题的id
    private String title; //问题名称
    private String description; //问题描述
    private Long gmtCreate; //产生时间
    private Long gmtModified; //修改时间
    private Integer creator; //创建者
    private Integer commentCount; //评论数
    private Integer viewCount; //观看数
    private Integer likeCount; //点赞数
    private String tags; //标签
    private User user;
}
