package bitcamp.java89.ems2.control;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletContext;

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
import bitcamp.java89.ems2.domain.Photo;
import bitcamp.java89.ems2.domain.Teacher;
import bitcamp.java89.ems2.util.MultipartUtil;

@Controller
public class TeacherControl {
  @Autowired ServletContext sc;
  
  @Autowired  TeacherDao teacherDao; // 이 인터페이슬르 구현한 DAO 자동으로 찾아서 꼽아줌.
  @Autowired MemberDao memberDao;
  @Autowired StudentDao studentDao;
  @Autowired ManagerDao managerDao;
  
@RequestMapping("/teacher/list")
public String list(Model model) throws Exception {
  ArrayList<Teacher> list = teacherDao.getList(); // Dao로부터 받은 list
  model.addAttribute("teachers", list);
  model.addAttribute("title", "강사관리-목록"); // main.jsp에 보내기전에 titl과 contentPage 정해서 보내준다.
  model.addAttribute("contentPage", "/teacher/list.jsp");
  return "main";
  // 나머지는 공통적으로 DispatcherServlet이 함.
}

@RequestMapping("/teacher/detail")
public String detail(int memberNo, Model model) throws Exception {
  Teacher teacher = teacherDao.getOneWithPhoto(memberNo);   
  if (teacher == null) {
    throw new Exception("해당 강사가 없습니다.");
  }
  
  model.addAttribute("teacher", teacher);
  model.addAttribute("title", "강사관리-상세정보");
  model.addAttribute("contentPage", "/teacher/detail.jsp");
  return "main";
  
}
  
@RequestMapping("/teacher/add")
public String add(Teacher teacher, MultipartFile[] photo) throws Exception {
  

  if (teacherDao.count(teacher.getEmail()) != 0) {
    throw new Exception("이메일이 존재합니다. 등록을 취소합니다.");
  }
  
  if (memberDao.count(teacher.getEmail()) == 0) { // 학생이나 매니저로 등록되지 않았다면,
    memberDao.insert(teacher);
    
  } else { // 학생이나 매니저로 이미 등록된 사용자라면 기존의 회원 번호를 사용한다.
    Member member = memberDao.getOne(teacher.getEmail());
    teacher.setMemberNo(member.getMemberNo());
  }
  ArrayList<Photo> photoList = new ArrayList<>();
  for (MultipartFile file : photo) {
    if (file.getSize() > 0 ) { // 0보다 크다는건 파일이 업로드 되었다는 뜻.
      String newFilename = MultipartUtil.generateFilename();
      file.transferTo(new File(sc.getRealPath("/upload/" + newFilename))); // 
      photoList.add(new Photo(newFilename)); 
    }
  }
  
  teacher.setPhotoList(photoList);
  
  teacherDao.insert(teacher);
  teacherDao.insertWithPhoto(teacher); 
  
  return "redirect:list.do";
  
}

@RequestMapping("/teacher/delete")
public String delete(int memberNo) throws Exception {

  if (teacherDao.countByNo(memberNo) == 0) {
    throw new Exception("학생을 찾지 못했습니다.");
  }
  teacherDao.delete(memberNo);

  if (managerDao.countByNo(memberNo) == 0 && studentDao.countByNo(memberNo) == 0) {
    memberDao.delete(memberNo);
  }

  return "redirect:list.do";
}


@RequestMapping("/teacher/update")
public String update(Teacher teacher, MultipartFile[] photo) throws Exception {
  
  if (teacherDao.countByNo(teacher.getMemberNo()) == 0) {
    throw new Exception("강사를 찾지 못했습니다.");
  }
  
  memberDao.update(teacher);
  ArrayList<Photo> photoList = new ArrayList<>();
  for (MultipartFile file : photo) {
    if (file.getSize() > 0 ) { // 0보다 크다는건 파일이 업로드 되었다는 뜻.
      String newFilename = MultipartUtil.generateFilename();
      file.transferTo(new File(sc.getRealPath("/upload/" + newFilename))); // 
      photoList.add(new Photo(newFilename)); 
    }
  }
  teacher.setPhotoList(photoList);
  
  teacherDao.update(teacher);
  
  return "redirect:list.do";

}


}
