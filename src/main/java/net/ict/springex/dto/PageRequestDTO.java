package net.ict.springex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Arrays;

//페이지 처리는 현재페이지번호(page),한페이지당 데이터수(size) 기본적으로 필요
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    private String[] types;//제목,작성자 중 골라라
    private String keyword; //검색어 처리
    private boolean finished; //완료여부와 기간은 and필터링
    private LocalDate from;
    private LocalDate to;

    @Builder.Default//page의 기본값
    @Min(value=1) //기본값 가장최소값 1
    @Positive //양수 (음수처리안하겟다)
    private int page=1;

    @Builder.Default//size의 기본값
    @Min(value=10)//데이터패치= 10개씩 가져오고
    @Max(value=100) //
    @Positive
    private int size=10;//10개씩 처리한다.


    public int getSkip(){//현재페이지에서 앞으로 가기
        return (page-1)*100;
    }

    public boolean checkType(String type){
        if(types==null || types.length==0)
        {
            return false;
        }
        return Arrays.stream(types).anyMatch(type::equals);
    }
    public String getLink(){//화면에서 모든 링크에서 수정하는것을 방지한다?
        StringBuilder builder=new StringBuilder();
        builder.append("page="+this.page);
        builder.append("&size="+this.size);
        if(finished){
            builder.append("&finished=on");
        }
        if(types !=null && types.length>0)
        {
            for(int i=0;i<types.length;i++){
                builder.append("&type="+types[i]);
            }
        }
        if(keyword!=null)
            try{
                builder.append("&keyword="+ URLEncoder.encode(keyword,"UTF-8"));
            }catch(UnsupportedEncodingException e) {
                e.printStackTrace();
            }//list에 붙여서 처리해라 키워드로 들어올때 한글 처리
        if(from!=null)
        {
            builder.append("&from="+from.toString());
        }
        if(to!=null)
        {
            builder.append("&to="+to.toString());
        }
        return builder.toString();//내가지금까지만든 쿼리스트링 보내준다.
    }


}
