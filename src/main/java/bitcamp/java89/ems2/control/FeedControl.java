package bitcamp.java89.ems2.control;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import bitcamp.java89.ems2.dao.ContentDao;
import bitcamp.java89.ems2.dao.FeedDao;
import bitcamp.java89.ems2.dao.ManagerDao;
import bitcamp.java89.ems2.dao.StudentDao;
import bitcamp.java89.ems2.dao.TagDao;
import bitcamp.java89.ems2.dao.TeacherDao;
import bitcamp.java89.ems2.domain.Feed;
import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.domain.Tag;

@Controller
public class FeedControl {
  @Autowired FeedDao feedDao;
  @Autowired ContentDao contentDao; 
  @Autowired TagDao tagDao; 
  @Autowired ManagerDao managerDao;
  @Autowired TeacherDao teacherDao;
  @Autowired StudentDao studentDao;
  
  
  @RequestMapping("/feed/list.do")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ArrayList<Feed> list = feedDao.getList();

    request.getSession().setAttribute("urlPath", "../feed/form.jsp"); // 로그인 후 우리 페이지로 되돌아오기
    
    request.setAttribute("feeds", list);
    request.setAttribute("title", "피드-관리목록");
    request.setAttribute("contentPage", "/feed/list.jsp");
    return "/main.jsp";
  }
  
  @RequestMapping("/feed/add.do")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member member = (Member)request.getSession().getAttribute("member");

    if (member == null) {
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/auth/loginform.jsp");
      requestDispatcher.forward(request, response);
    }

    Feed feed = new Feed();
    feed.setContents(request.getParameter("conts")); 
    Tag tag = new Tag();
    tag.setTagName(request.getParameter("tagName"));
    feed.setMemberNo(member.getMemberNo());

    contentDao.insert(feed);

    feedDao.insert(feed);

    tag.setContentNo(feed.getContentNo());
    tagDao.insert(tag);

    return "redirect:list.do";
  }
  
  @RequestMapping("/feed/delete.do")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int contentsNo = Integer.parseInt(request.getParameter("contentNo"));
    feedDao.delete(contentsNo);
    return "redirect:list.do";

  }  
  
  @RequestMapping("/feed/detail.do")
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int contentNo = Integer.parseInt(request.getParameter("ContentNo"));

    Feed feed = feedDao.getOne(contentNo);

    if (feed == null) {
      throw new Exception("해당 학생이 없습니다.");
    }
    request.setAttribute("feed", feed); 
    request.setAttribute("title", "피드 - 상세");
    request.setAttribute("contentPage", "/feed/detail.jsp");
    return "/main.jsp";
  }
  
  @RequestMapping("/feed/update.do")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Feed feed = new Feed();
    feed.setContentNo(Integer.parseInt(request.getParameter("cono")));
    feed.setContents(request.getParameter("conts"));
    
    System.out.println("-->" + request.getParameter("cono") + request.getParameter("conts"));

    feedDao.update(feed);

    return "redirect:list.do";
  }
}
