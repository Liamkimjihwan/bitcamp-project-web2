package bitcamp.java89.ems2.control;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import bitcamp.java89.ems2.domain.Student;
import bitcamp.java89.ems2.service.StudentService;
import bitcamp.java89.ems2.util.MultipartUtil;

@Controller
public class StudentControl {
  @Autowired ServletContext sc;
  
  @Autowired StudentService studentService;
  
  @RequestMapping("/student/form")
  public String form(Model model) {
    model.addAttribute("title", "학생 입력폼"); // main.jsp에 보내기전에 titl과 contentPage 정해서 보내준다.
    model.addAttribute("contentPage", "student/form.jsp"); // main을 기준으로 상대 경로 지정.
    return "main";
  }
  
  @RequestMapping("/student/list")
  public String list(Model model) throws Exception {
    List<Student> list = studentService.getList(); // Dao로부터 받은 list
    model.addAttribute("students", list);
    model.addAttribute("title", "학생관리-목록"); // main.jsp에 보내기전에 titl과 contentPage 정해서 보내준다.
    model.addAttribute("contentPage", "student/list.jsp");
    return "main";
    // 나머지는 공통적으로 DispatcherServlet이 함.
  }
  @RequestMapping("/student/detail")
  public String detail(int memberNo, Model model) throws Exception {
    Student student = studentService.getDetail(memberNo);   
    if (student == null) {
      throw new Exception("해당 학생이 없습니다.");
    }

       // 페이지 컨트롤러는 모델 객체가 리턴한 값을 JSP가 출력할 수 있도록 가공하는 일을 아래와 같이 한다.
    model.addAttribute("student", student); // JSP가 사용할 데이터를 가공한다. = page Controller의 역할
    model.addAttribute("title", "학생관리-상세정보");
    model.addAttribute("contentPage", "student/detail.jsp");
    return "main";

  }
 
  @RequestMapping("/student/add")
  public String add(Student student, MultipartFile photo) throws Exception {

    // 페이지 컨트롤러는 입력 파라미터 값을 가공하여 모델 객체에게 전달하는 역할을 한다.
    // 또한 모델 객체가 리턴한 값을 
    if (photo.getSize() > 0 ) { 
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename))); // 파일을 저장하고. 
      student.setPhotoPath(newFilename); // 파일명은 student에 박아논다.
    }
    
    studentService.add(student);
    return "redirect:list.do";
  }
  
  @RequestMapping("/student/delete")
  public String delete(int memberNo) throws Exception {
     studentService.delete(memberNo);
    return "redirect:list.do";
  }
  @RequestMapping("/student/update")
  public String update(Student student, MultipartFile photo) throws Exception {
    if (photo.getSize() > 0 ) { // 0보다 크다는건 파일이 업로드 되었다는 뜻.
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename))); // 
      student.setPhotoPath(newFilename); 
    }
    studentService.update(student);
    
    return "redirect:list.do";
  }
}
