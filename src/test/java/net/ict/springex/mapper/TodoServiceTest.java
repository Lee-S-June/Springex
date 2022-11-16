package net.ict.springex.mapper;

import lombok.extern.log4j.Log4j2;
import net.ict.springex.dto.PageRequestDTO;
import net.ict.springex.dto.PageResponseDTO;
import net.ict.springex.dto.TodoDTO;
import net.ict.springex.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/root-context.xml")
public class TodoServiceTest {
    @Autowired
    private TodoService todoService;

    @Test
    public void testRegister(){
        TodoDTO todoDTO=TodoDTO.builder()
                .title("Test todoDTO")
                .dueDate(LocalDate.now())
                .writer("ict01")
                .build();

        todoService.register(todoDTO);
    }
    @Test
    public void testPaging(){
        PageRequestDTO pageRequestDTO=PageRequestDTO.builder()
                .page(1).size(10).build();//두가지값 넘김

        PageResponseDTO<TodoDTO> responseDTO=todoService.getList(pageRequestDTO);
        log.info(responseDTO);
        responseDTO.getDtoList().stream().forEach(todoDTO -> log.info(todoDTO));
    }
}
