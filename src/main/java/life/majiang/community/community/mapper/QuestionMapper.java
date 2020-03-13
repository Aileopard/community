package life.majiang.community.community.mapper;

import life.majiang.community.community.Model.Question;
import life.majiang.community.community.dto.QuestionDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tags) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tags})")
    public void create(Question question);

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> GetList(@Param("offset")Integer offset, @Param("size")Integer size);

    // 获取总问题数目
    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset}, #{size}")
    List<Question> GetListByUserId(@Param("userId")Integer userId, @Param("offset")Integer offset, @Param("size")Integer size);

    // 根据用户id查询该用户的提问数目
    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tags = #{tags} where id = #{id}")
    void updateQuestion(Question question);
}
