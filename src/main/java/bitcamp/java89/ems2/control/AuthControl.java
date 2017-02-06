package bitcamp.java89.ems2.control;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.service.AuthService;

@Controller
public class AuthControl {
  
  @Autowired AuthService authService;
  
  @RequestMapping("/auth/login")
  public String login(String email, String password, String userType, boolean saveEmail, HttpServletResponse response, HttpSession session, Model model) throws Exception {
    if (saveEmail) {
      // 쿠키를 웹 브라우저에게 보낸다.
      Cookie cookie = new Cookie("email", email);
      cookie.setMaxAge(60 * 60 * 24 * 15);
      response.addCookie(cookie);
      
    } else {
      // 기존에 보낸 쿠키를 제거하라고 웹 브라우저에게 응답한다.
      Cookie cookie = new Cookie("email", "");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

      Member member = authService.getMemberInfo(email, password, userType);
    if (member == null) {
      response.setHeader("Refresh", "2;url=loginform.do");
      model.addAttribute("title", "로그인");
      model.addAttribute("contentPage", "auth/loginfail.jsp");
      return "main";
    }
       session.setAttribute("member", member);
       return "redirect:../student/list.do";
      }
    
  
  
  @RequestMapping("/auth/loginform")
  public String loginform(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setAttribute("title", "로그인");
    request.setAttribute("contentPage", "auth/loginform.jsp");
    return "main";
  }
  
  @RequestMapping("/auth/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.getSession().invalidate(); // 기존 세션을 무효화시킨다.
    return "redirect:loginform.do";
  }
}







