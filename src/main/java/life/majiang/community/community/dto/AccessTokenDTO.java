package life.majiang.community.community.dto;

import lombok.Data;

//这是访问令牌
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
