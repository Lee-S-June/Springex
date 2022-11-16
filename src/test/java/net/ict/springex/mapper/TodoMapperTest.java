package net.ict.springex.mapper;

import lombok.extern.log4j.Log4j2;
import net.ict.springex.domain.TodoVO;
import net.ict.springex.dto.PageRequestDTO;
import net.ict.springex.dto.PageResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/root-context.xml")
public class TodoMapperTest {
    @Autowired(required = false)
    private TodoMapper todoMapper;

    @Test
    public void testNow(){
        log.info(todoMapper.getTime());
    }


    @Test
    public void testInsert(){
        TodoVO todoVO=TodoVO.builder()
                .title("spring테스트")
                .dueDate(LocalDate.of(2022,11,14))
                .writer("ict05")
                .build();

        todoMapper.insert(todoVO);
    }

    @Test
    public void testSelect(){
        List<TodoVO> voList=todoMapper.selectAll();
        voList.forEach(vo->log.info(vo));//한 row씩 꺼내옴
    }
    @Test
    public void testDelete(){
        TodoVO todoVO=TodoVO.builder()
                .tno((6L))
                .build();

        todoMapper.delete(todoVO.getTno());
    }

    @Test
    public void testSelectList(){
        PageRequestDTO pageRequestDTO= PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        List<TodoVO> voList=todoMapper.selectList(pageRequestDTO);//todomapper.xml에 값 넣음
        voList.forEach(vo->log.info(vo));
    }
    @Test
    public void testSelectSearch(){
        PageRequestDTO pageRequestDTO=PageRequestDTO.builder()
                .page(1)
                .size(10)
                .types(new String[]{"t","w"})
                .keyword("스프링")
                .from(LocalDate.of(2022,11,10))
                .to(LocalDate.of(2022,11,14))
                .finished(true)
                .build();
        List<TodoVO> voList=todoMapper.selectList(pageRequestDTO);
        voList.forEach(vo->log.info(vo));
        log.info(todoMapper.getCount(pageRequestDTO));
    }


}
