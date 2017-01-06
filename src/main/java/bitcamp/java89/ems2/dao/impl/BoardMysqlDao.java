package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bitcamp.java89.ems2.dao.BoardDao;
import bitcamp.java89.ems2.domain.Board;
import bitcamp.java89.ems2.util.DataSource;


@Repository("boardDao")
public class BoardMysqlDao implements BoardDao {
  @Autowired DataSource ds;

  public void delete(int contentsNo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
      PreparedStatement stmt = con.prepareStatement(
          "delete from board where bdno=?"); ) {
      
      stmt.setInt(1, contentsNo);
      
      stmt.executeUpdate();
      
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public ArrayList<Board> getList() throws Exception {
    ArrayList<Board> list = new ArrayList<>();
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select bdno, titl, name, rdt, vw_cnt" +
              " from board" + 
              " left outer join content on content.cono=board.bdno" +
              " left outer join memb on memb.mno=content.mno");
      ResultSet rs = stmt.executeQuery(); ){
      
      while (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Board board = new Board();
        board.setContentNo(rs.getInt("bdno"));
        board.setTitle(rs.getString("titl"));
        board.setName(rs.getString("name"));
        board.setRegisterDate(rs.getString("rdt"));
        board.setViewCount(rs.getInt("vw_cnt"));
        
        list.add(board);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }
  
  
  public Board getOne(int contentsNo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
        /*
          select titl, conts, memb.name, rdt, vw_cnt
          from board
          left outer join content on board.bdno=content.cono
          left outer join memb on content.mno=memb.mno
          where bdno=?
         */
      PreparedStatement stmt = con.prepareStatement(
          "select titl, conts, memb.name, rdt, vw_cnt"
          + " from board"
          + " left outer join content on board.bdno=content.cono"
          + " left outer join memb on content.mno=memb.mno"
          + " where bdno=?");) {

      stmt.setInt(1, contentsNo);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Board board = new Board();
        board.setContentNo(contentsNo);
        board.setTitle(rs.getString("titl"));
        board.setContents(rs.getString("conts"));
        board.setName(rs.getString("memb.name"));
        board.setRegisterDate(rs.getString("rdt"));
        board.setViewCount(rs.getInt("vw_cnt"));
        rs.close();
        return board;
        
      } else {
        rs.close();
        return null;
      }
    } finally {
      ds.returnConnection(con);
    }
  }
  
  
  @Override
  public void update(Board board) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "update board set"
          + " titl=?, conts=?"
          + " where bdno=?"); ) {
      
      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContents());
      stmt.setInt(3, board.getContentNo());
      
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }


  
  
  
  public void updateViewCount(int viewCount, int contentNo) throws Exception {
	    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
	    try (
	      PreparedStatement stmt = con.prepareStatement(
	          "update content set"
	          + " vw_cnt=?"
	          + " where cono=?"); ) {
	      
	      stmt.setInt(1, viewCount);
	      stmt.setInt(2, contentNo);
	      
	      stmt.executeUpdate();
	    } finally {
	      ds.returnConnection(con);
	    }
	  }



  @Override
  public boolean exist(int contentsNo) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select count(*) from board where bdno=?"); ) {
      
      stmt.setInt(1, contentsNo);
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
  
  public void insert(Board board) throws Exception {
    Connection con = ds.getConnection(); 
    try (
      PreparedStatement stmt = con.prepareStatement(
          "insert into board(bdno, titl, conts) values (?,?,?)");) {
      
      
      stmt.setInt(1, board.getContentNo());
      stmt.setString(2, board.getTitle());
      stmt.setString(3, board.getContents());
      stmt.executeUpdate();
  
      
    } finally {
      ds.returnConnection(con);
    }
  }
}


