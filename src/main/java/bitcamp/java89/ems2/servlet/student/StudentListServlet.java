package bitcamp.java89.ems2.servlet.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/student/list")
public class StudentListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // 클라이언트에게 데이터 송신시 사용할 출력 스트림

    // ArrayList<Student> list = new ArrayList<>(); 리턴할 때만 필요함
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();



    Connection con = null;
    PreparedStatement stat = null;
    ResultSet rs = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      con =  DriverManager.getConnection("jdbc:mysql://localhost:8080/java89db","java89", "1111");
      stat = con.prepareStatement(
          "select mno, name, email, work, schl_nm from memb join stud on memb.mno=stud.sno;");
      rs = stat.executeQuery();

      // try () { Class.forName("com.mysql.jdbc.Driver");

      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<meta name='viewport' content='width=device-width, user-scalable=no,"
          + "maximm-scale=1'>");
      out.println("<title>학생관리-목록</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>학생 정보</h1>");
      out.println("<a href='../form.html'>추가</a>");
      out.println("<table border='1'>");
      out.println("<tr>");  
      out.println("<th> 학생번호</th><th> 이름</th><th> 이메일</th><th> 재직여부</th><th> 학교이름</th>");
      out.println("</tr>");

      while (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        out.println("<tr>"); 
        out.printf("<td><a href= 'view?mno=%d'>%1$s</a></td><td>%s</td><td>%s</td><td>%s</td>\n",
            rs.getString("mno"), // %s가 하나 더 생겼기 때문에 userId에 
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("work"),
            rs.getString("schl_nm"));
        out.println("</tr>");

      }
      
      out.println("</table>");
      out.println("</body>");
      out.println("</html>");

    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      try {rs.close();} catch (Exception e) {}
      try {stat.close();} catch (Exception e) {}
      try {con.close();} catch (Exception e) {}
    }
  }
}
