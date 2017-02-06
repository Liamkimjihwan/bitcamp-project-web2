package bitcamp.java89.ems2.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bitcamp.java89.ems2.domain.Manager;
import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.domain.Photo;
import bitcamp.java89.ems2.domain.Student;
import bitcamp.java89.ems2.domain.Teacher;


@WebFilter("*.do") // 모든 .do만 받는다.
public class LoginCheckFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest)req;
    HttpServletResponse response = (HttpServletResponse)resp;
    
    String servletPath = request.getServletPath();
    
   // 로그인, 로그아웃을 요청한 경우 로그인 여부를 검사히지 않는다.
    if (!servletPath.startsWith("/auth/")) {
      // /auth/로 시작하지 않는 경우를 세션을 꺼내서 멤버가 들어있지 않으면 센드리다이렉트 시킨다.

    // HttpSession 꺼내기
    HttpSession session = request.getSession();
    Member member = (Member)session.getAttribute("member");

    if (member == null ) {
      response.sendRedirect(request.getContextPath() + "/auth/loginform.do");
      return;
      // getContextPath() 가 리턴하는건 Http ~~ 8080/~web2/student/list.do 에서 
      // ~web2 => 웹 어플리케이션 경로   // /student/list.do => 서블릿 경로(Path)
       }
    }
    chain.doFilter(request, response);
  }


  @Override
  public void destroy() {

  }


}