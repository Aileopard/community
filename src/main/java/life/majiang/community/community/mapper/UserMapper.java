package life.majiang.community.community.mapper;

import life.majiang.community.community.Model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountID},#{token},#{gmtCreate},#{gmtModified})")
    public void insert(User user);

    //如果里面的参数不是类，则需要加上注解@Param
    @Select("select * from user where token = #{token}")
    public User findByToken(@Param("token") String token);

}
