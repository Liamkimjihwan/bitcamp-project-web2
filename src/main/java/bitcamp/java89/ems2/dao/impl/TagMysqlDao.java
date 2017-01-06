package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bitcamp.java89.ems2.dao.TagDao;
import bitcamp.java89.ems2.domain.Content;
import bitcamp.java89.ems2.domain.Tag;
import bitcamp.java89.ems2.util.DataSource;

@Repository("tagDao")
public class TagMysqlDao implements TagDao {
  @Autowired DataSource ds;


  public ArrayList<Tag> getList() throws Exception {
    ArrayList<Tag> list = new ArrayList<>();
    Connection con = ds.getConnection();

    try (
        PreparedStatement stmt = con.prepareStatement("select distinct tag_nm from tag;");
        ResultSet rs = stmt.executeQuery();){

      while (rs.next()) {
        Tag tag = new Tag();
        tag.setTagName(rs.getString("tag_nm"));
        list.add(tag);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }


  //  public Tag getOne(int contentNo) throws Exception {
  //    Connection con = ds.getConnection(); 
  //    try (
  //        PreparedStatement stmt = con.prepareStatement(
  //            "select tag_nm, rdt, vw_cnt,"
  //                + " from tag"
  //                + " left outer join content on tag.cono=content.cono"
  //                + " where cono=?");) {
  //  
  //      stmt.setInt(1, contentNo);
  //      ResultSet rs = stmt.executeQuery();
  //  
  //      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
  //        Tag tag= new Tag();
  //        tag.setContentNo(contentNo);
  //        tag.setTagName(rs.getString("tag_nm"));
  //        tag.setRegisterDate(rs.getString("rdt"));
  //        tag.setViewCount(rs.getInt("vw_cnt"));
  //  
  //        rs.close();
  //        return tag;
  //  
  //      } else {
  //        rs.close();
  //        return null;
  //      }
  //    } finally {
  //      ds.returnConnection(con);
  //    }
  //  }


  public boolean exist(int contentNo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
        PreparedStatement stmt = con.prepareStatement(
            "select count(*)"
                + " from tag left outer join content on tag.cono=content.cono"
                + " where cono=?"); ) {

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

  public void insert(Tag tag) throws Exception {
    Connection con = ds.getConnection(); 

    try (
        PreparedStatement stmt = con.prepareStatement(
            "insert into tag(cono, tag_nm) values(?, ?)"); ) {

      String[] tags = tag.getTagName().trim().split("#"); 

      for (String tagNm : tags) {
        if (!"".equals(tagNm)){
          stmt.setInt(1, tag.getContentNo());
          stmt.setString(2, tagNm);
          stmt.executeUpdate();
          System.out.println(tagNm);
        }
      }
    } finally {
      ds.returnConnection(con);
    }
  }

  public ArrayList<Tag> getOne(String tagName) throws Exception {
    ArrayList<Tag> list = new ArrayList<>();
    Connection con = ds.getConnection(); 
    try (
        PreparedStatement stmt = con.prepareStatement(
            // "select content.cono, mno, rdt, vw_cnt"
            "select tag.cono, mno, rdt, vw_cnt"
                + " from tag"
                + " left outer join content on content.cono=tag.cono where tag_nm=?");) {

      stmt.setString(1, tagName);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Tag tag= new Tag();
        // tag.setContentNo(rs.getInt("content.cono"));
        tag.setContentNo(rs.getInt("tag.cono"));
        tag.setMemberNo(rs.getInt("mno"));
        tag.setRegisterDate(rs.getString("rdt"));
        tag.setViewCount(rs.getInt("vw_cnt"));
        list.add(tag);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }
  
  public ArrayList<Tag> getOne(int ContentNo) throws Exception {
    ArrayList<Tag> list = new ArrayList<>();
    Connection con = ds.getConnection(); 
    try (
        PreparedStatement stmt = con.prepareStatement(
            "select tag_nm"
                + " from tag where cono=?");) {

      stmt.setInt(1, ContentNo);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Tag tag= new Tag();
        tag.setTagName(rs.getString("tag_nm"));

        list.add(tag);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }

  public void update(Tag tag) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
        PreparedStatement stmt = con.prepareStatement(
            "update tag set"
                + " tag_nm=?"
                + " where cono=?"); ) {

      stmt.setString(1, tag.getTagName());
      stmt.setInt(2, tag.getContentNo());

      stmt.executeUpdate();

    } finally {
      ds.returnConnection(con);
    }
  }

  public void delete(Content content) throws Exception {
    Connection con = ds.getConnection(); 
    try (
        PreparedStatement stmt = con.prepareStatement(
            "delete from tag where cono=?"); ) {

      stmt.setInt(1, content.getContentNo());

      stmt.executeUpdate();

    } finally {
      ds.returnConnection(con);
    }
  }

  public void delete(int contentNo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
        PreparedStatement stmt = con.prepareStatement(
            "delete from tag where cono=?"); ) {

      stmt.setInt(1, contentNo);

      stmt.executeUpdate();

    } finally {
      ds.returnConnection(con);
    }
  } /* this method needed - sinaeYu */
  
  
  public String getContent(int contentNo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
        PreparedStatement stmt = con.prepareStatement(
            "select todo.conts, proj.titl, feed.conts, board.titl, code.conts"
                + " from content"
                + " left outer join todo on todo.tdno=content.cono"
                + " left outer join proj on proj.pjno=content.cono"
                + " left outer join feed on feed.fdno=content.cono"
                + " left outer join board on board.bdno=content.cono"
                + " left outer join code on code.cdno=content.cono"
                + " where content.cono=?;");) {

      stmt.setInt(1, contentNo);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        if (rs.getString("todo.conts") != null) {
          return rs.getString("todo.conts");
        } else if (rs.getString("proj.titl") != null) {
          return rs.getString("proj.titl");
        } else if (rs.getString("feed.conts") != null) {
          return rs.getString("feed.conts");
        } else if (rs.getString("board.titl") != null) {
          return rs.getString("board.titl");
        } else if (rs.getString("code.conts") != null) {
          return rs.getString("code.conts");
        }
      }

    } finally {
      ds.returnConnection(con);
    }
    return null;
  }
}

/*
insert into content values(1, 1, now(), 0);
insert into content values(2, 2, now(), 0);
insert into content values(3, 3, now(), 0);
insert into content values(4, 4, now(), 0);
insert into content values(5, 5, now(), 0);
insert into content values(6, 6, now(), 0);
insert into content values(7, 7, now(), 0);
insert into content values(8, 8, now(), 0);
insert into content values(9, 9, now(), 0);
insert into content values(10, 10, now(), 0);
insert into content values(11, 10, now(), 0);
insert into content values(12, 10, now(), 0);
insert into content values(13, 10, now(), 0);
insert into content values(14, 21, now(), 0);
insert into content values(15, 22, now(), 0);
insert into content values(16, 23, now(), 0);

insert into tag values(1, 1, "자바");
insert into tag values(2, 3, "자바스크립트");
insert into tag values(3, 8, "MySQL");
insert into tag values(4, 13, "HTML");
insert into tag values(5, 15, "CSS");
insert into tag values(6, 2, "자바");
insert into tag values(7, 4, "자바스크립트");
insert into tag values(8, 9, "MySQL");
insert into tag values(9, 14, "HTML");
insert into tag values(10, 16, "CSS");
insert into tag values(11, 15, "CSS");
insert into tag values(12, 16, "CSS");
 */
