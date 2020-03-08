package life.majiang.community.community.mapper;

import life.majiang.community.community.Model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.GetMapping;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tags) values (#{title}, #{description}, #{gmt_create}, #{gmt_modified}, #{creator}, #{tags})")
    public void create(Question question);
}
