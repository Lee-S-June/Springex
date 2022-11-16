package net.ict.springex.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import java.time.LocalDate;

//객체 자료형운 파라미터로 처리하기 위해서는 객체로 생성되고
//setxxx()이용해서 처리
@ToString
@Data
@Builder
@AllArgsConstructor //생성자
@NoArgsConstructor //기본생성자
public class TodoDTO {

    private Long tno;
    @NotEmpty//빈문자열 안됨
    private String title;
    @Future //햇는지 안했는지 확인 현재보다 미래인값이 들어왓는지(과거 안됨)
    private LocalDate dueDate;
    private boolean finished;
    @NotEmpty
    private String writer; //작성자를 의미

}
