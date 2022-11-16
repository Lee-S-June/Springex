package net.ict.springex.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO <E>{
    //페이지에 관련된 모든데이터타입을 DTO에 담을수 있도록 했다.
    //제너릭을 이용한 이유는 다른 종류의 객체를 이용하여 PageResponseDTO를 구성할수 있도록
    //예로 회원정보게시판이나 공지사항도 페이징 처리가 필요하므로 공통처리를 위해 제너릭<E>로 처리
    private int page;
    private int size;
    private int total;

    //시작 페이지 번호
    private int start;
    //끝 페이지 번호
    private int end;

    //이전페이지 존재여부
    private boolean prev;

    //다음페이지 존재여부
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName="withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO,List<E> dtoList,int total)
    {
        this.page= pageRequestDTO.getPage();
        this.size=pageRequestDTO.getSize();
        this.total=total;
        this.dtoList=dtoList;
        //마지막 페이지(end)를 구하는 계산식
        // end는 현재의 페이지 번호를 기준으로 계산
        this.end=(int)(Math.ceil(this.page/10.0))*10;
        this.start=this.end-9;
    //시작 페이지를 구성한 후 마지막 페이지는 다시 전체개수(total)을 고려하여
        // 만약 10개씩 (size)를 보여주는 경우
        //전체 갯수(total)를 구하여 last를 구해야한다.
        //만약 전체 개수수가 75개라면 마지막페이지는 75/10=>7.5=>8
        int last=(int)(Math.ceil(total/(double)size));//전체 페이징 번호(100/10=10페이지나온다)
        //마지막 페이지(end)검사: 앞에서 구한 last값 보다 작은경우는 last가 end가 된다.
        this.end=end>last?last:end;//last가 end보다 작으면 end에 last값이 들어간다.
        //이전 페이지(prev)존재 여부는 시작페이지가 1이 아니라면 무조건 true
        this.prev=this.start>1;//시작페이지가 1보다크면 이전페이지가 생김
        //다음페이지는 마지막 페이지(end)와 페이지당 수(size)를 곱한 값보다 전채개수(total)이 더많은지 체크하여 판단
        this.next=total>this.end * this.size; //total이 크면 다음 페이지블록을 만들 필요없다

    }
}
