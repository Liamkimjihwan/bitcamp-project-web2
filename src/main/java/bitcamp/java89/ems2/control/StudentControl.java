package bitcamp.java89.ems2.control;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import bitcamp.java89.ems2.dao.ManagerDao;
import bitcamp.java89.ems2.dao.MemberDao;
import bitcamp.java89.ems2.dao.StudentDao;
import bitcamp.java89.ems2.dao.TeacherDao;
import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.domain.Student;
import bitcamp.java89.ems2.util.MultipartUtil;

@Controller
public class StudentControl {
  @Autowired ServletContext sc;
  
  @Autowired MemberDao memberDao;
  @Autowired StudentDao studentDao; // 이 인터페이슬르 구현한 DAO 자동으로 찾아서 꼽아줌.
  @Autowired TeacherDao teacherDao;
  @Autowired ManagerDao managerDao;
  
  @RequestMapping("/student/list")
  public String list(Model model) throws Exception {
    ArrayList<Student> list = studentDao.getList(); // Dao로부터 받은 list
    model.addAttribute("students", list);
    model.addAttribute("title", "학생관리-목록"); // main.jsp에 보내기전에 titl과 contentPage 정해서 보내준다.
    model.addAttribute("contentPage", "/student/list.jsp");
    return "main";
    // 나머지는 공통적으로 DispatcherServlet이 함.
  }
  @RequestMapping("/student/detail")
  public String detail(int memberNo, Model model) throws Exception {
    Student student = studentDao.getOne(memberNo);   
    if (student == null) {
      throw new Exception("해당 학생이 없습니다.");
    }

    model.addAttribute("student", student);
    model.addAttribute("title", "학생관리-상세정보");
    model.addAttribute("contentPage", "/student/detail.jsp");
    return "main";

  }

  @RequestMapping("/student/add")
  public String add(Student student, MultipartFile photo) throws Exception {

    if ((studentDao.count(student.getEmail()) > 0)) {
      throw new Exception("같은 학생의 이메일이 존재합니다. 등록을 취소합니다.");
    }

    
    
    if (memberDao.count(student.getEmail()) == 0) { // 강사나 매니저로 등록되지 않았다면,
      memberDao.insert(student);

    } else { 
      Member member = memberDao.getOne(student.getEmail());
      student.setMemberNo(member.getMemberNo());
    }

    if (photo.getSize() > 0 ) { // 0보다 크다는건 파일이 업로드 되었다는 뜻.
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename))); // 
      student.setPhotoPath(newFilename); 
    }
    
    studentDao.insert(student);

    return "redirect:list.do";

  }
  
  @RequestMapping("/student/delete")
  public String delete(int memberNo) throws Exception {
    if (studentDao.countByNo(memberNo) == 0) {
      throw new Exception("학생을 찾지 못했습니다.");
    }
    
    studentDao.delete(memberNo);

    if (managerDao.countByNo(memberNo) == 0 && teacherDao.countByNo(memberNo) ==0) {
      memberDao.delete(memberNo);
    }
    return "redirect:list.do";
  }
  
  @RequestMapping("/student/update")
  public String update(Student student, MultipartFile photo) throws Exception {
    // 알아서 Student 생성해서 꼽아준다.
    
    if (studentDao.countByNo(student.getMemberNo()) == 0) {
      throw new Exception("학생을 찾지 못했습니다.");
    }
    
    memberDao.update(student);
    
    if (photo.getSize() > 0 ) { // 0보다 크다는건 파일이 업로드 되었다는 뜻.
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename))); // 
      student.setPhotoPath(newFilename); 
    }
    studentDao.update(student);
    
    return "redirect:list.do";
  }
}
