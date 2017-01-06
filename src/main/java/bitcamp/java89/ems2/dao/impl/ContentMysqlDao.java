package bitcamp.java89.ems2.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bitcamp.java89.ems2.dao.ContentDao;
import bitcamp.java89.ems2.domain.Content;
import bitcamp.java89.ems2.util.DataSource;

@Repository("contentDao")
public class ContentMysqlDao implements ContentDao {
  @Autowired DataSource ds;
  
  
  public ArrayList<Content> getList() throws Exception {
    ArrayList<Content> list = new ArrayList<>();
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
        PreparedStatement stmt = con.prepareStatement(
            "select cono, mno, rdt, vw_cnt from content");
        ResultSet rs = stmt.executeQuery(); ){

      while (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Content content = new Content();
        content.setContentNo(rs.getInt("cono"));
        content.setMemberNo(rs.getInt("mno"));
        content.setRegisterDate(rs.getString("rdt"));
        content.setViewCount(rs.getInt("vw_cnt"));

        list.add(content);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }
  
  

  public void insert(Content content) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
        PreparedStatement stmt = con.prepareStatement(
            "insert into content(mno, rdt, vw_cnt) values(?,now(),?)", 
        Statement.RETURN_GENERATED_KEYS); ) {

      stmt.setInt(1, content.getMemberNo());
      stmt.setInt(2, 0);
      stmt.executeUpdate();
      
      
      ResultSet keyRS = stmt.getGeneratedKeys();
      keyRS.next();
      content.setContentNo(keyRS.getInt(1));
      keyRS.close();
      

    } finally {
      ds.returnConnection(con);
    }
  } 

  public Content getOne(int contentNo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
        PreparedStatement stmt = con.prepareStatement(
            "select cono, mno, rdt, vw_cnt"
                + " from content where cono=?");) {

      stmt.setInt(1, contentNo);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Content content = new Content();
        content.setContentNo(rs.getInt("cono"));
        content.setMemberNo(rs.getInt("mno"));
        content.setRegisterDate(rs.getString("rdt"));
        content.setViewCount(rs.getInt("vw_cnt"));
        rs.close();
        return content;

      } else {
        rs.close();
        return null;
      }
    } finally {
      ds.returnConnection(con);
    }
  }

  public void update(Content content) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
        PreparedStatement stmt = con.prepareStatement(
            "update content set"
                + " cono=?, mno=?, rdt=?, vw_cnt=?"
                + " where cono=?"); ) {

      stmt.setInt(1, content.getContentNo());
      stmt.setInt(2, content.getMemberNo());
      stmt.setString(3, content.getRegisterDate());
      stmt.setInt(4, content.getViewCount());
      stmt.executeUpdate();

    } finally {
      ds.returnConnection(con);
    }
  }
  

  public void delete(int contentNo) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
        PreparedStatement stmt = con.prepareStatement(
            "delete from content where cono=?"); ) {

      stmt.setInt(1, contentNo);

      stmt.executeUpdate();

    } finally {
      ds.returnConnection(con);
    }
  }

  public void downdelete(int downNo) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
        PreparedStatement stmt = con.prepareStatement(
            "delete from dowload where dnno=?"); ) {

      stmt.setInt(1, downNo);

      stmt.executeUpdate();

    } finally {
      ds.returnConnection(con);
    }
  }


  @Override
  public boolean exist(int contentNo) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
        PreparedStatement stmt = con.prepareStatement(
            "select count(*) from content where cono=?"); ) {

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
}