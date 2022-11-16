package net.ict.springex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.ict.springex.dto.PageRequestDTO;
import net.ict.springex.dto.TodoDTO;
import net.ict.springex.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/todo")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;//생성자 주입

//    @RequestMapping("/list")
//    public void list(Model model)
//    {
//        log.info("todo list..........");
//        model.addAttribute("dtoList",todoService.getAll());//model에는 'dtoList'이름으로 목록데이터가 담겨 있다.
//
//    }

    @RequestMapping("/list")
    public void list(@Valid PageRequestDTO pageRequestDTO, BindingResult bindingResult, Model model) {
        log.info(pageRequestDTO);
        if (bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build();
        }
        model.addAttribute("responseDTO", todoService.getList(pageRequestDTO));
        //pageRequestDTO를 담아서 getList호출
        //model에는 'dtolist'이름으로 목록 데이터 담겨있따.
    }

    @RequestMapping(value="/register",method= RequestMethod.GET)
    public void registerGET()
    {
        log.info("todo register.....");
    }


    //todoregister를 post방식으로 처리하는 메서드
    //글등록이후 todolist로 이동해라
    @PostMapping("/register")//todoDTO는 검증대상이다.(notemty,future해놓은거 검사)
    public String registerPost(@Valid TodoDTO todoDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes)//bindingResult추가됨
    {
        log.info("Post todo register.....");

        if(bindingResult.hasErrors()){//퓨처와 노엠티에 에러가 있다면
            log.error("has error...........");
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());//한번쓰고 날아간다.(유지 x)
            return "redirect:/todo/register";//다시 쓰는 곳으로 이동
        }

        log.info(todoDTO);
        todoService.register(todoDTO);//주입
        return "todo/list_before";
    }

    @GetMapping({"/read","/modify"})
    public void read(Long tno,Model model){
        TodoDTO todoDTO=todoService.getOne(tno);
        log.info(todoDTO);
        model.addAttribute("dto",todoDTO);
    }

    @PostMapping("/remove")
    public String remove(Long tno,RedirectAttributes redirectAttributes)
    {
        log.info("-------------remove---------------");
        log.info("tno:" +tno);
        todoService.remove(tno);
        return "todo/list_before";
    }

    @PostMapping("/modify")
    public String modify(@Valid TodoDTO todoDTO,BindingResult bindingResult,RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors()){//에러있으면 자세한 에러 로그 보여주고 modify로 돌아가기
            log.info("has errors.....");
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            redirectAttributes.addAttribute("tno",todoDTO.getTno());
            return "redirect:/todo/modify";
        }
        log.info(todoDTO);
        todoService.modify(todoDTO);
        return "todo/list_before";
    }



}
