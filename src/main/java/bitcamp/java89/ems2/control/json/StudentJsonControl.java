package bitcamp.java89.ems2.control.json;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bitcamp.java89.ems2.domain.Student;
import bitcamp.java89.ems2.service.StudentService;
import bitcamp.java89.ems2.util.MultipartUtil;

//@Controller
@RestController  //이 애노테이션을 붙이면, 스프링 설정 파일에 
                 //JSON 변환기 'MappingJackson2JsonView' 객체를 등록하지 않아도 된다.
public class StudentJsonControl {
  @Autowired ServletContext sc;
  
  @Autowired StudentService studentService;
  
  @RequestMapping("/student/form")
  public String form(Model model) {
    model.addAttribute("title", "학생 입력폼"); // main.jsp에 보내기전에 titl과 contentPage 정해서 보내준다.
    model.addAttribute("contentPage", "student/form.jsp"); // main을 기준으로 상대 경로 지정.
    return "main";
  }
  
  @RequestMapping("/student/list")
  public AjaxResult list() throws Exception { // model은 필요없음. 문자열로 바꾸
    List<Student> list = studentService.getList();
    return new AjaxResult(AjaxResult.SUCCESS, list);
    // 나머지는 공통적으로 DispatcherServlet이 함.
  }
  @RequestMapping("/student/detail")
  public AjaxResult detail(int memberNo) throws Exception { // JSP에 전달할 필요가 없어서 model은 필요 없음.
    Student student = studentService.getDetail(memberNo);   
    if (student == null) {
      return new AjaxResult(AjaxResult.FAIL, "해당 학생이 없습니다.");
    }
    return new AjaxResult(AjaxResult.SUCCESS, student); // student DB 값

  }
 
  @RequestMapping("/student/add")
  public AjaxResult add(Student student, MultipartFile photo) throws Exception {

    // 페이지 컨트롤러는 입력 파라미터 값을 가공하여 모델 객체에게 전달하는 역할을 한다.
    // 또한 모델 객체가 리턴한 값을 
    if (photo != null && photo.getSize() > 0 ) { 
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename))); // 파일을 저장하고. 
      student.setPhotoPath(newFilename); // 파일명은 student에 박아논다.
    }
    
    studentService.add(student);
    
    return new AjaxResult(AjaxResult.SUCCESS, "등록 성공입니다.");
  }
  
  @RequestMapping("/student/delete")
  public AjaxResult delete(int memberNo, HttpServletRequest request) throws Exception {
     int count = studentService.delete(memberNo);
     if (count == 0) {
       return new AjaxResult(AjaxResult.FAIL, "해당 번호의 학생이 없습니다.");
     }
    return new AjaxResult(AjaxResult.SUCCESS, "삭제 성공");
  }
  @RequestMapping("/student/update")
  public AjaxResult update(Student student, MultipartFile photo) throws Exception {
    if (photo != null && photo.getSize() > 0) { // 파일이 업로드 되었다면,
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename)));
      student.setPhotoPath(newFilename);
    }
    int count = studentService.update(student);
    if (count == 0) {
      return new AjaxResult(AjaxResult.FAIL, "해당 번호의 학생이 없습니다.");
    }
    
    return new AjaxResult(AjaxResult.SUCCESS, "변경 성공입니다.");
  }
}
