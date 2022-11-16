package net.ict.springex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.ict.springex.domain.TodoVO;
import net.ict.springex.dto.PageRequestDTO;
import net.ict.springex.dto.PageResponseDTO;
import net.ict.springex.dto.TodoDTO;
import net.ict.springex.mapper.TodoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//데이터 베이스를 처리하는 TodoMapper와 DTO,VO변환을 처리하는 ModelMapper주입
@Service
@Log4j2
@RequiredArgsConstructor //의존성 주입이 필요한 객체의 타입을 final로 고정하고
// 생성자 @ReqriredArgsConstructor을 이용하여 생성자로 생성하는 방식 사용
public class TodoServiceImpl implements TodoService {
    private final TodoMapper todoMapper;
    private final ModelMapper modelMapper;


    public void register (TodoDTO todoDTO)
    {
        log.info(modelMapper);
        TodoVO todoVo=modelMapper.map(todoDTO,TodoVO.class);
        log.info(todoVo);
        todoMapper.insert(todoVo);
    }

//    @Override
//    public List<TodoDTO> getAll() {
//        List<TodoDTO> dtoList=todoMapper.selectAll().stream()//매퍼를 통해서 selectAll을한row들이 vo에 담앗엇다.
//                            .map(vo->modelMapper.map(vo,TodoDTO.class))//vo는 modelmapper를 통해서 dto타입으로 바꾸고
//                            .collect(Collectors.toList());//컬렉터를 통해서 리스트로 테이블로 변환해줌.
//        return dtoList;
//        //List<TodoVO>를 List<TodoDTO>로 변환하는 작업을 stream을 이용하여
//        // 각 TodoVO는 map()을 통해서 TodoDTO로 바꾸고
//        //Collect()을 이용하여 List<TodoDTO>로 묶어준다.
//    }


    @Override
    public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO) {
        List<TodoVO> voList=todoMapper.selectList(pageRequestDTO);
        //VO로 받은거 DTO로 바꾸기
        List<TodoDTO> dtoList=voList.stream()
                .map(vo->modelMapper.map(vo,TodoDTO.class))
                .collect(Collectors.toList());

        int total=todoMapper.getCount(pageRequestDTO);
        PageResponseDTO<TodoDTO> pageResponseDTO= PageResponseDTO.<TodoDTO>withAll()//withAll메서드 빌딩
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public TodoDTO getOne(Long tno) {
        TodoVO todoVO=todoMapper.selectOne(tno);
        TodoDTO todoDTO=modelMapper.map(todoVO,TodoDTO.class);

        return todoDTO;
    }

    @Override
    public void remove(Long tno) {

        todoMapper.delete(tno);
    }

    @Override
    public void modify(TodoDTO todoDTO) {
        TodoVO todoVO=modelMapper.map(todoDTO,TodoVO.class);
        todoMapper.update(todoVO);

    }
}
