package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bitcamp.java89.ems2.dao.TodoDao;
import bitcamp.java89.ems2.domain.Todo;
import bitcamp.java89.ems2.util.DataSource;
@Repository("todoDao")
public class TodoMysqlDao implements TodoDao {
  @Autowired DataSource ds;
  
 
  @Override
  public ArrayList<Todo> getList() throws Exception {
    ArrayList<Todo> list = new ArrayList<>();
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
     
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select tdno, seq, name, titl, stat, rdt "
          + " from todo left outer join content on content.cono=todo.tdno "
          + " left outer join proj on proj.pjno=todo.pjno "
          + " left outer join memb on memb.mno=content.cono;");
//          "select mno, seq, name, stat,rdt "
//        + "from memb left outer join content on content.cono=memb.mno "
//        + "left outer join proj on proj.pjno=memb.tdno "
//        + "left outer join todo on todo.tdno=memb.mno;");
          
      ResultSet rs = stmt.executeQuery(); ){
      
      while (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Todo todo = new Todo();
        todo.setTodoNo(rs.getInt("tdno"));
        todo.setSequence(rs.getInt("seq"));
        todo.setName(rs.getString("name"));
        todo.setTitle(rs.getString("titl"));
        todo.setState(rs.getString("stat"));
        todo.setRegisterDate(rs.getString("rdt"));
        
        list.add(todo);
        
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }

  public boolean exist(int contentNo) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select count(*)"
          + " from todo left outer join content on todo.tdno=content.cono"
          + " where content.cono=?"); ) {
      
      stmt.setInt(1, contentNo);
      ResultSet rs = stmt.executeQuery();
      
      rs.next();
      int count = rs.getInt(1);
      rs.close();
      
      if (count > 0) {
        return true;
      } else {
        return false;
      }
      
    } finally {
      ds.returnConnection(con);
    }
  }
 
  public void insert(Todo todo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
        /*
         insert into todo(tdno,seq,tdconts,stat,stdt,mno) 
         values(6,1,'hj','jk',date_format(now(), '%Y-%m-%d %h:%i'),1)"
         */
      PreparedStatement stmt = con.prepareStatement(
          "insert into todo(tdno,seq,tdconts,stat,stdt,mno,pjno) "
          + "values(?,?,?,?,now(),?,?)"
              
              ); ) {
      stmt.setInt(1, todo.getContentNo());
      stmt.setInt(2, todo.getSequence());
      stmt.setString(3, todo.getTdContents());
      stmt.setString(4, todo.getState());
      stmt.setInt(5, todo.getMemberNo());
      stmt.setInt(6, todo.getProjectNo());
      stmt.executeUpdate();
      

    } finally {
      ds.returnConnection(con);
    }
  }
 

  public Todo getOne(int contentNo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select titl, name, tel, email, tdconts, rdt, vw_cnt "
          + " from todo left outer join memb on todo.mno=memb.mno "
          + " left outer join content on todo.tdno=content.cono "
          + " left outer join proj on todo.pjno=proj.pjno "
          + " where tdno=?");) {

      stmt.setInt(1, contentNo);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Todo todo = new Todo();
        todo.setContentNo(contentNo);
        todo.setTitle(rs.getString("titl"));
        todo.setName(rs.getString("name"));
        todo.setTel(rs.getString("tel"));
        todo.setEmail(rs.getString("email"));
        todo.setTdContents(rs.getString("tdconts"));
        todo.setRegisterDate(rs.getString("rdt"));
        todo.setViewCount(rs.getInt("vw_cnt"));
        rs.close();
        return todo;
        
      } else {
        rs.close();
        return null;
      }
    } finally {
      ds.returnConnection(con);
    }
  }
  
  
  public void update(Todo todo) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "update todo set"
          + " seq=?, tdconts=?, stat=?, stdt=?"
          + " where tdno=?"); ) {
      
      stmt.setInt(1, todo.getSequence());
      stmt.setString(2, todo.getTdContents());
      stmt.setString(3, todo.getState());
      stmt.setString(4, todo.getStateDate());
      stmt.setInt(5, todo.getTodoNo());
      
      stmt.executeUpdate();
      
    } finally {
      ds.returnConnection(con);
    }
  }

  
  public void delete(int contentNo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
      PreparedStatement stmt = con.prepareStatement(
          "delete from todo where tdno=?"); ) {
      
      stmt.setInt(1, contentNo);
      
      stmt.executeUpdate();
      
    } finally {
      ds.returnConnection(con);
    }
  }


@Override
public boolean exist(String email) throws Exception {
  // TODO Auto-generated method stub
  return false;
}

  
}

