package life.majiang.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOList;
    private boolean showPrevious; //是否有向前按钮
    private boolean showFirstPage; //是否有第一页按钮
    private boolean showNext; //是否有向后按钮
    private boolean showEndPage; //是否有最后一页按钮

    private Integer page; //当前页
    private List<Integer> pages = new ArrayList<>(); //包含当前批显示的所有页数，size
    private Integer totalpage; //总页数
    // totalCount：问题总数目
    // page：当前页
    // size：每页的问题数目
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        this.page = page;

        // 每批显示size页，那么有多少批？
        if(totalCount % size == 0){
            totalpage = totalCount /size;
        }else {
            totalpage = totalCount / size +1;
        }

        if(page > totalpage){
            page = totalpage;
        }

        if(page < 1){
            page = 1;
        }

        pages.add(page);
        for(int i =1; i<=3; i++){
            // 从page往插入
            if(page - i >0){
                pages.add(0,page-i);
            }

            // 从page往后插入
            if(page + i <= totalpage){
                pages.add(page + i);
            }
        }

        // 是否展示上一页，当第一页时，不展示下一页。
        if(page == 1){
            showPrevious = false;
        }else{
            showPrevious = true;
        }

        // 是否展示下一页，当最后一页时，不展示下一页。
        if(page == totalpage){
            showNext = false;
        }else{
            showNext = true;
        }

        // 是否展示第一页，当该批中不包含第一页时
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }

        // 是否展示最后一页,当该批中不包含最后一页时
        if(pages.contains(totalpage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }

    }
}
