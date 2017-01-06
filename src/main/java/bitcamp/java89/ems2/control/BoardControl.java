
package bitcamp.java89.ems2.control;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import bitcamp.java89.ems2.dao.BoardDao;
import bitcamp.java89.ems2.dao.ContentDao;
import bitcamp.java89.ems2.dao.DownloadDao;
import bitcamp.java89.ems2.domain.Board;
import bitcamp.java89.ems2.domain.Download;
import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.util.MultipartUtill;

@Controller
public class BoardControl { 
  @Autowired BoardDao boardDao;
  @Autowired DownloadDao downloadDao;
  @Autowired ContentDao contentDao;
  
  @RequestMapping("/board/list.do")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ArrayList<Board> list = boardDao.getList();
    
    request.getSession().setAttribute("urlPath", "../board/form.jsp"); // 로그인 후 우리 페이지로 되돌아오기
    
    request.setAttribute("board", list);
    request.setAttribute("title", "게시판관리-목록");
    request.setAttribute("contentPage", "/board/list.jsp");

    return "/main.jsp";
  }
 
  
  @RequestMapping("/board/add.do")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String, String> dataMap = MultipartUtill.parse(request);

    Board board = new Board();
    Download download = new Download();
//    board.setContentNo(Integer.parseInt(request.getParameter("contentsNo")));
    board.setTitle(dataMap.get("title"));
    board.setContents(dataMap.get("contents"));
    download.setPath(dataMap.get("filePath"));

    Member member = (Member)request.getSession().getAttribute("member");
    board.setMemberNo(member.getMemberNo());
    download.setMemberNo(member.getMemberNo());

    contentDao.insert(board);
    boardDao.insert(board);

    if (download.getPath() != null) {
      downloadDao.insert(download);
    }
    return "redirect:list.do";
  }
  @RequestMapping("/board/delete.do")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int boardNo = Integer.parseInt(request.getParameter("contentNo"));
    int contentNo = Integer.parseInt(request.getParameter("contentNo"));
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    boardDao.delete(boardNo);
    contentDao.delete(contentNo);
    
    out.println("<p>삭제하였습니다.</p>");
  
    // FooterServlet에게 꼬리말 HTML 생성을 요청한다.
    return "redirect:list.do";
  }  
  
  @RequestMapping("/board/detail.do")
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int contentNo = Integer.parseInt(request.getParameter("contentNo"));
    
    Board board = boardDao.getOne(contentNo);
    
    if (board == null) {
      throw new Exception("게시글이 존재하지 않습니다.");
    }
    
    request.setAttribute("board", board);
    request.setAttribute("title", "세부정보");
    request.setAttribute("contentPage", "/board/detail.jsp");
    return "/main.jsp";
  }
  
  @RequestMapping("/board/update.do")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String,String> dataMap = MultipartUtill.parse(request);
    
    Board board = new Board();
    board.setContentNo(Integer.parseInt(dataMap.get("contentNo")));
    board.setTitle(dataMap.get("title"));
    board.setContents(dataMap.get("contents"));
    
    if (!boardDao.exist(board.getContentNo())) {
      throw new Exception("해당 글을 찾지 못했습니다.");
    }
    
    contentDao.update(board);
    boardDao.update(board);

    return "redirect:list.do";
  }
}
