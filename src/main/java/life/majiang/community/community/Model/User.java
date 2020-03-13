package life.majiang.community.community.Model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountID;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
