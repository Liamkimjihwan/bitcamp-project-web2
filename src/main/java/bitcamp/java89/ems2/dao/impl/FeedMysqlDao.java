package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bitcamp.java89.ems2.dao.FeedDao;
import bitcamp.java89.ems2.domain.Feed;
import bitcamp.java89.ems2.util.DataSource;

@Repository("feedDao")
public class FeedMysqlDao implements FeedDao {
  @Autowired DataSource ds;
  
  public ArrayList<Feed> getList() throws Exception {
    ArrayList<Feed> list = new ArrayList<>();
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.

    try (
        PreparedStatement stmt = con.prepareStatement(
            "select memb.mno, content.cono, memb.name, rdt, vw_cnt,conts from feed " 
                + " left outer join content on feed.fdno=content.cono"
                + " left outer join memb on content.mno=memb.mno"
                + " order by rdt desc");        ){

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Feed feed = new Feed();
        feed.setMemberNo(rs.getInt("mno"));
        feed.setContentNo(rs.getInt("cono"));
        feed.setName(rs.getString("name"));
        feed.setRegisterDate(rs.getString("rdt"));
        feed.setViewCount(rs.getInt("vw_cnt"));
        feed.setContents(rs.getString("conts"));

        list.add(feed);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }

  public void insert(Feed feed) throws Exception {
    Connection con = ds.getConnection(); 
    try (
      PreparedStatement stmt = con.prepareStatement(
          "insert into feed(fdno, conts) values(?, ?)");) {
      
      stmt.setInt(1, feed.getContentNo());
      stmt.setString(2, feed.getContents());
      stmt.executeUpdate();
      
    } finally {
      ds.returnConnection(con);
    }
  } 

  public Feed getOne(int contentsNo) throws Exception {
    Connection con = ds.getConnection(); 

    try (
        PreparedStatement stmt = con.prepareStatement(
            "select memb.mno, memb.name, rdt, vw_cnt, conts"
                +  " from feed"
                + " left outer join content on feed.fdno=content.cono"
                + " left outer join memb on content.mno=memb.mno"
                + " where cono=?");) {

      stmt.setInt(1, contentsNo);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Feed feed = new Feed();

        feed.setContentNo(contentsNo);
        feed.setMemberNo(rs.getInt("mno"));
        feed.setName(rs.getString("name"));
        feed.setRegisterDate(rs.getString("rdt"));
        feed.setViewCount(rs.getInt("vw_cnt"));
        feed.setContents(rs.getString("conts"));

        rs.close();
        return feed;

      } else {
        rs.close();
        return null;
      }
    } finally {
      ds.returnConnection(con);
    }
  }

  public void delete(int contentsNo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
        PreparedStatement stmt = con.prepareStatement(
            "delete from feed where fdno=?"); ) {

      stmt.setInt(1, contentsNo);

      stmt.executeUpdate();

    } finally {
      ds.returnConnection(con);
    }
  }

  public void updateCount(int viewCount, int contentNo) throws Exception {
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

  public void update(Feed feed) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
        PreparedStatement stmt = con.prepareStatement(
            "update feed set"
                + "  conts=?"
                + " where fdno=?"); ) {

      stmt.setString(1, feed.getContents());
      stmt.setInt(2, feed.getContentNo());


      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }
}
