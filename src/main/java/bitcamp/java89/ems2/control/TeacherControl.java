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
import bitcamp.java89.ems2.domain.Photo;
import bitcamp.java89.ems2.domain.Teacher;
import bitcamp.java89.ems2.util.MultipartUtill;

@Controller
public class TeacherControl {
  @Autowired  TeacherDao teacherDao; // 이 인터페이슬르 구현한 DAO 자동으로 찾아서 꼽아줌.
  @Autowired MemberDao memberDao;
  @Autowired StudentDao studentDao;
  @Autowired ManagerDao managerDao;
  
@RequestMapping("/teacher/list.do")
public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
  ArrayList<Teacher> list = teacherDao.getList(); // Dao로부터 받은 list
  request.setAttribute("teachers", list);
  request.setAttribute("title", "강사관리-목록"); // main.jsp에 보내기전에 titl과 contentPage 정해서 보내준다.
  request.setAttribute("contentPage", "/teacher/list.jsp");
  return "/main.jsp";
  // 나머지는 공통적으로 DispatcherServlet이 함.
}
  
@RequestMapping("/teacher/add.do")
public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
  Map<String,String> dataMap = MultipartUtill.parse(request);
  
  Teacher teacher = new Teacher();
  teacher.setEmail(dataMap.get("email"));
  teacher.setPassword(dataMap.get("password"));
  teacher.setName(dataMap.get("name"));
  teacher.setTel(dataMap.get("tel"));
  teacher.setHomepage(dataMap.get("homepage"));
  teacher.setFacebook(dataMap.get("facebook"));
  teacher.setTwitter(dataMap.get("twitter"));
  
  ArrayList<Photo> photoList = new ArrayList<>();
  photoList.add(new Photo(dataMap.get("photoPath1")));
  photoList.add(new Photo(dataMap.get("photoPath2")));
  photoList.add(new Photo(dataMap.get("photoPath3")));
  
  teacher.setPhotoList(photoList);
  

  if (teacherDao.exist(teacher.getEmail())) {
    throw new Exception("이메일이 존재합니다. 등록을 취소합니다.");
  }
  
  if (!memberDao.exist(teacher.getEmail())) { // 학생이나 매니저로 등록되지 않았다면,
    memberDao.insert(teacher);
    
  } else { // 학생이나 매니저로 이미 등록된 사용자라면 기존의 회원 번호를 사용한다.
    Member member = memberDao.getOne(teacher.getEmail());
    teacher.setMemberNo(member.getMemberNo());
  }
  
  teacherDao.insert(teacher);
  
  return "redirect:list.do";
  
}

@RequestMapping("/teacher/delete.do")
public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
  int memberNo = Integer.parseInt(request.getParameter("memberNo"));

  if (!teacherDao.exist(memberNo)) {
    throw new Exception("학생을 찾지 못했습니다.");
  }
  teacherDao.delete(memberNo);

  if (!managerDao.exist(memberNo) && !studentDao.exist(memberNo)) {
    memberDao.delete(memberNo);
  }

  return "redirect:list.do";
}

@RequestMapping("/teacher/detail.do")
public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
  int memberNo = Integer.parseInt(request.getParameter("memberNo"));
  Teacher teacher = teacherDao.getOne(memberNo);   
  if (teacher == null) {
    throw new Exception("해당 강사가 없습니다.");
  }

  request.setAttribute("teacher", teacher);
  return "/teacher/detail.jsp";

}

@RequestMapping("/teacher/update.do")
public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
Map<String,String> dataMap = MultipartUtill.parse(request);
  
  Teacher teacher = new Teacher();
  teacher.setMemberNo(Integer.parseInt(dataMap.get("memberNo")));
  teacher.setEmail(dataMap.get("email"));
  teacher.setPassword(dataMap.get("password"));
  teacher.setName(dataMap.get("name"));
  teacher.setTel(dataMap.get("tel"));
  teacher.setHomepage(dataMap.get("homepage"));
  teacher.setFacebook(dataMap.get("facebook"));
  teacher.setTwitter(dataMap.get("twitter"));
  
  ArrayList<Photo> photoList = new ArrayList<>();
  photoList.add(new Photo(dataMap.get("photoPath1")));
  photoList.add(new Photo(dataMap.get("photoPath2")));
  photoList.add(new Photo(dataMap.get("photoPath3")));
  
  teacher.setPhotoList(photoList);
  

  
  if (!teacherDao.exist(teacher.getMemberNo())) {
    throw new Exception("강사를 찾지 못했습니다.");
  }
  
  memberDao.update(teacher);
  
  teacherDao.update(teacher);
  
  return "redirect:list.do";

}


}
