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
import bitcamp.java89.ems2.domain.Manager;
import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.util.MultipartUtill;

@Controller
public class ManagerControl {
  @Autowired  ManagerDao managerDao; // 이 인터페이슬르 구현한 DAO 자동으로 찾아서 꼽아줌.
  @Autowired MemberDao memberDao;
  @Autowired TeacherDao teacherDao;
  @Autowired StudentDao studentDao;
@RequestMapping("/manager/list.do")
public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
  ArrayList<Manager> list = managerDao.getList(); // Dao로부터 받은 list
  request.setAttribute("managers", list);
  request.setAttribute("title", "매니저관리-목록"); // main.jsp에 보내기전에 titl과 contentPage 정해서 보내준다.
  request.setAttribute("contentPage", "/manager/list.jsp");
  return "/main.jsp";
}
  
@RequestMapping("/manager/add.do")
public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
  Map<String,String> dataMap = MultipartUtill.parse(request);
  
  Manager manager = new Manager();
  manager.setEmail(dataMap.get("email"));
  manager.setPassword(dataMap.get("password"));
  manager.setName(dataMap.get("name"));
  manager.setTel(dataMap.get("tel"));
  manager.setPosition(dataMap.get("position"));
  manager.setFax(dataMap.get("fax"));
  manager.setPhotoPath(dataMap.get("photoPath") == null ? "default.png" : dataMap.get("photoPath"));
  
  if (managerDao.exist(manager.getEmail())) {
    throw new Exception("같은 매니저 이메일이 존재합니다. 등록을 취소합니다.");
  }
  
  if (!memberDao.exist(manager.getEmail())) { // 강사나 매니저로 등록되지 않았다면,
    memberDao.insert(manager);
    
  } else { // 강사나 매니저로 이미 등록된 사용자라면 기존의 회원 번호를 사용한다.
    Member member = memberDao.getOne(manager.getEmail());
    manager.setMemberNo(member.getMemberNo());
  }
  managerDao.insert(manager);
  
  return "redirect:list.do";
  
}

@RequestMapping("/manager/detail.do")
public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
  int memberNo = Integer.parseInt(request.getParameter("memberNo"));
  Manager manager = managerDao.getOne(memberNo);   
  if (manager == null) {
    throw new Exception("해당 매니저가 없습니다.");
  }

  request.setAttribute("manager", manager);
  return "/manager/detail.jsp";

}

@RequestMapping("/manager/delete.do")
public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
  int memberNo = Integer.parseInt(request.getParameter("memberNo"));

  if (!managerDao.exist(memberNo)) {
    throw new Exception("학생을 찾지 못했습니다.");
  }
  managerDao.delete(memberNo);

  if (!teacherDao.exist(memberNo) && !studentDao.exist(memberNo)) {
    memberDao.delete(memberNo);
  }

  return "redirect:list.do";
}

@RequestMapping("/manager/update.do")
public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
Map<String,String> dataMap = MultipartUtill.parse(request);
     
     Manager manager = new Manager();
     manager.setMemberNo(Integer.parseInt(dataMap.get("memberNo")));
     manager.setEmail(dataMap.get("email"));
     manager.setPassword(dataMap.get("password"));
     manager.setName(dataMap.get("name"));
     manager.setTel(dataMap.get("tel"));
     manager.setPosition(dataMap.get("position"));
     manager.setFax(dataMap.get("fax"));
     manager.setPhotoPath(dataMap.get("photoPath") == null ? "default.png" : dataMap.get("photoPath"));
     
     if (!managerDao.exist(manager.getMemberNo())) {
       throw new Exception("사용자를 찾지 못했습니다.");
     }
     memberDao.update(manager);
     managerDao.update(manager);
     
     return "redirect:list.do";
   
   }

}
