package net.ict.springex.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper { //시간 정보를 문자열로 처리 할수 있게
    @Select("select now()") //어노테이션으로 sql처리(mapper interface)
    String getTime();
}
