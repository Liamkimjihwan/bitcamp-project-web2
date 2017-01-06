package bitcamp.java89.ems2.control;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import bitcamp.java89.ems2.dao.ManagerDao;
import bitcamp.java89.ems2.dao.MemberDao;
import bitcamp.java89.ems2.dao.StudentDao;
import bitcamp.java89.ems2.dao.TeacherDao;
import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.domain.Student;
import bitcamp.java89.ems2.util.MultipartUtill;

@Controller
public class StudentControl {
  @Autowired MemberDao memberDao;
  @Autowired StudentDao studentDao; // 이 인터페이슬르 구현한 DAO 자동으로 찾아서 꼽아줌.
  @Autowired TeacherDao teacherDao;
  @Autowired ManagerDao managerDao;
  @RequestMapping("/student/list.do")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ArrayList<Student> list = studentDao.getList(); // Dao로부터 받은 list
    request.setAttribute("students", list);
    request.setAttribute("title", "학생관리-목록"); // main.jsp에 보내기전에 titl과 contentPage 정해서 보내준다.
    request.setAttribute("contentPage", "/student/list.jsp");
    return "/main.jsp";
    // 나머지는 공통적으로 DispatcherServlet이 함.
  }
  @RequestMapping("/student/detail.do")
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    Student student = studentDao.getOne(memberNo);   
    if (student == null) {
      throw new Exception("해당 학생이 없습니다.");
    }

    request.setAttribute("student", student);
    request.setAttribute("title", "학생관리-상세정보");
    request.setAttribute("contentPage", "/student/detail.jsp");
    return "/main.jsp";

  }

  @RequestMapping("/student/add.do")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String,String> dataMap = MultipartUtill.parse(request);

    Student student = new Student();
    student.setEmail(dataMap.get("email"));
    student.setPassword(dataMap.get("password"));
    student.setName(dataMap.get("name"));
    student.setTel(dataMap.get("tel"));
    student.setWorking(Boolean.parseBoolean(dataMap.get("working")));
    student.setGrade(dataMap.get("grade"));
    student.setSchoolName(dataMap.get("schoolName"));
    student.setPhotoPath(dataMap.get("photoPath"));


    if (studentDao.exist(student.getEmail())) {
      throw new Exception("같은 학생의 이메일이 존재합니다. 등록을 취소합니다.");
    }

    if (!memberDao.exist(student.getEmail())) { // 강사나 매니저로 등록되지 않았다면,
      memberDao.insert(student);

    } else { 
      Member member = memberDao.getOne(student.getEmail());
      student.setMemberNo(member.getMemberNo());
    }

    studentDao.insert(student);

    return "redirect:list.do";

  }
  
  @RequestMapping("/student/delete.do")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    if (!studentDao.exist(memberNo)) {
      throw new Exception("학생을 찾지 못했습니다.");
    }
    studentDao.delete(memberNo);

    if (!managerDao.exist(memberNo) && !teacherDao.exist(memberNo)) {
      memberDao.delete(memberNo);
    }
    return "redirect:list.do";
  }
  
  @RequestMapping("/student/update.do")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String,String> dataMap = MultipartUtill.parse(request);
    
    Student student = new Student();
    student.setMemberNo(Integer.parseInt(dataMap.get("memberNo")));
    student.setEmail(dataMap.get("email"));
    student.setPassword(dataMap.get("password"));
    student.setName(dataMap.get("name"));
    student.setTel(dataMap.get("tel"));
    student.setWorking(Boolean.parseBoolean(dataMap.get("working")));
    student.setGrade(dataMap.get("grade"));
    student.setSchoolName(dataMap.get("schoolName"));
    student.setPhotoPath(dataMap.get("photoPath"));
    
    if (!studentDao.exist(student.getMemberNo())) {
      throw new Exception("학생을 찾지 못했습니다.");
    }
    
    memberDao.update(student);
    studentDao.update(student);
    
    return "redirect:list.do";
  }
}
