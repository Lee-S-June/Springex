package net.ict.springex.controller.exception;

import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

@ControllerAdvice//advice는 어떤컨트롤러 클래스들보다 먼저 처리된다(우선순위높다)
@Log4j2
public class CommonExceptionAdvice {
//    @ResponseBody//응답바디에 (브라우저)
//    @ExceptionHandler(NumberFormatException.class)//(숫자가아닌 다른 타입이 왓을때) 에러가 발생했을때
//    public String exceptNumber(NumberFormatException numberFormatException){
//    log.error("==============================");
//    log.error(numberFormatException.getMessage());
//    return "NUMBER FORMAT EXCEPTION";//브라우저에 바로 전달
//    }
    //예외처리의 최상위 타입인 Exception타입을 처리하도록 구성,범용적 에러처리 방식

    @ResponseBody//응답바디에 (브라우저)
    @ExceptionHandler(Exception.class)//에러를 자세히 보여준다.
    public String exceptCommon(Exception exception){
        log.error("============error======== ========");
        log.error(exception.getMessage());
        StringBuffer buffer=new StringBuffer("<ul>");
        buffer.append("<li>"+exception.getMessage()+"</li>");
        Arrays.stream(exception.getStackTrace()).forEach(stackTraceElement->{
           buffer.append("<li>"+stackTraceElement+"</li>");

        });
        buffer.append("</ul>");
        return buffer.toString();//브라우저에 바로 전달
    }

    //해당 페이지가 없을때
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(){
        return "custom404";
    }

}
