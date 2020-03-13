package life.majiang.community.community.mapper;

import life.majiang.community.community.Model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountID},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    public void insert(User user);

    //如果里面的参数不是类，则需要加上注解@Param
    @Select("select * from user where token = #{token}")
    public User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    public User findById(@Param("id")Integer id);

    @Select("select * from user where account_id = #{accountID}")
    public User findByAccountID(@Param("accountID")String accountID);

    @Update("update user set name = #{name}, account_id = #{accountID}, token = #{token},avatar_url = #{avatarUrl},gmt_modified = #{gmtModified} where id = #{id}")
    public void updateUser(User user);
    
}
