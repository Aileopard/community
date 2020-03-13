package life.majiang.community.community.Service;

import life.majiang.community.community.Model.Question;
import life.majiang.community.community.Model.User;
import life.majiang.community.community.dto.PaginationDTO;
import life.majiang.community.community.dto.QuestionDTO;
import life.majiang.community.community.mapper.QuestionMapper;
import life.majiang.community.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    //获取所有的问题信息，显示在主页
    //page表示当前页是第几页，size表示每次显示几页。
    public PaginationDTO GetList(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        //获取数据库中所有的问题数目
        Integer totalCount = questionMapper.count();
        // 设置当前页的显示信息：是否前一页、后一页。。。
        paginationDTO.setPagination(totalCount, page, size);

        // 防止输入页数为负数和0的情况
        if(page < 1){
            page = 1;
        }
        // 防止输入页数大于总页数的情况
        if(page > paginationDTO.getTotalpage()){
            page = paginationDTO.getTotalpage();
        }

        //计算当前页的偏移量。
        Integer offset = size * (page - 1);
        // 获取当前页的所有问题
        List<Question> questionList = questionMapper.GetList(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //获取questionDTO
        for(Question question : questionList){
            //根据creator，来获取user，主要是为了avatar_url
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question中的成员变量的值复制到questionDTO中去
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        // 设置当前页的问题列表
        paginationDTO.setQuestionDTOList(questionDTOList);

        return paginationDTO;
    }

    // 根据用户id来查询用户的问题信息
    public PaginationDTO GetListByUserId(Integer userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        //根据用户id来获取数据库中该用户的问题数目
        Integer totalCount = questionMapper.countByUserId(userId);
        // 设置当前页的显示信息：是否前一页、后一页。。。
        paginationDTO.setPagination(totalCount, page, size);

        // 防止输入页数大于总页数的情况
        if(page > paginationDTO.getTotalpage()){
            page = paginationDTO.getTotalpage();
        }

        // 防止输入页数为负数和0的情况
        if(page < 1){
            page = 1;
        }

        //计算当前页的偏移量。
        Integer offset = size * (page - 1);
        // 获取当前页的所有问题
        List<Question> questionList = questionMapper.GetListByUserId(userId, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //获取questionDTO
        for(Question question : questionList){
            //根据creator，来获取user，主要是为了avatar_url
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question中的成员变量的值复制到questionDTO中去
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        // 设置当前页的问题列表
        paginationDTO.setQuestionDTOList(questionDTOList);

        return paginationDTO;
    }

    // 通过id找到问题的信息
    public QuestionDTO getById(Integer id) {

        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Integer id, Question question) {
        // 判断问题的id是否为空
        if(id != null){
            //更新问题，这里只需要更新问题的修改时间
            question.setGmtModified(System.currentTimeMillis());
            question.setId(id);
            questionMapper.updateQuestion(question);
        }else{
            // 添加问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }
    }
}
