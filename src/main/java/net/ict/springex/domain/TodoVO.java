package net.ict.springex.domain;

//MyBatis와 스프링을 이용한 영속처리
//1. VO 선언
//2. Mapper 인터페이스 개발
//3. XML 개발
//4. 테스트 코드 개발

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@Builder
@AllArgsConstructor //생성자
@NoArgsConstructor //기본생성자
public class TodoVO {
    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;
    private String writer; //작성자를 의미

}

