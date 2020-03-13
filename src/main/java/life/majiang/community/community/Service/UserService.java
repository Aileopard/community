package life.majiang.community.community.Service;

import life.majiang.community.community.Model.User;
import life.majiang.community.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {

        // 查询数据库中是否有account_id的用户
        User dbuser = userMapper.findByAccountID(user.getAccountID());
        if(dbuser != null){
            dbuser.setGmtModified(System.currentTimeMillis());
            dbuser.setAvatarUrl(user.getAvatarUrl());
            dbuser.setName(user.getName());
            dbuser.setToken(user.getToken());
            userMapper.updateUser(dbuser);
        }else {
            //设置为当前时间
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
    }
}
