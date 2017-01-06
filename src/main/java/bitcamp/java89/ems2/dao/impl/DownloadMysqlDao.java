package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bitcamp.java89.ems2.dao.DownloadDao;
import bitcamp.java89.ems2.domain.Download;
import bitcamp.java89.ems2.util.DataSource;

@Repository("downloadDao")
public class DownloadMysqlDao implements DownloadDao {
  @Autowired DataSource ds;
  
  // 파일 추가.
  public void insert(Download download) throws Exception {
    Connection con = ds.getConnection();

    try (
        PreparedStatement stmtContent = con.prepareStatement(
            "INSERT INTO CONTENT(MNO, RDT, VW_CNT) VALUES(?,CURDATE(),?)", Statement.RETURN_GENERATED_KEYS);
        PreparedStatement stmtDownload = con.prepareStatement("INSERT INTO DOWNLOAD(DNNO, PATH) VALUES(?,?)");) {

      stmtContent.setInt(1, download.getMemberNo());
      stmtContent.setInt(2, 0);
      stmtContent.executeUpdate();

      ResultSet keyRS = stmtContent.getGeneratedKeys();
      keyRS.next();
      stmtDownload.setInt(1, keyRS.getInt(1));
      stmtDownload.setString(2, download.getPath());
      stmtDownload.executeUpdate();

    } catch (Exception e) {
      System.out.println(e);
      throw new Exception("파일 추가중 에러");
    } finally {
      ds.returnConnection(con);
    }
  }

  // 리스트 출력
  public ArrayList<Download> getList() throws Exception {
    System.out.println("[download/getList]");
    ArrayList<Download> list = new ArrayList<>();
    Connection con = ds.getConnection();

    try (PreparedStatement stmt = con.prepareStatement(
        "select co.cono, do.path, co.mno, co.rdt" 
    + " from download do"
        + " left outer join content co"
    + " on do.dnno=co.cono"); ResultSet rs = stmt.executeQuery();) {

      while (rs.next()) {
        Download download = new Download();

        download.setContentNo(rs.getInt("cono"));
        download.setPath(rs.getString("path"));
        download.setMemberNo(rs.getInt("mno"));
        download.setRegisterDate(rs.getString("rdt"));
        list.add(download);

      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }

  // 특정 파일 데이터 조회
  public Download getOne(int contentNo) throws Exception {

    Connection con = ds.getConnection();
    try (PreparedStatement stmt = con.prepareStatement("select co.cono, co.mno, co.rdt, co.vw_cnt, do.path"
        + " from download do" + " left outer join content co" + " on do.dnno=co.cono" + " where co.cono=?");) {

      stmt.setInt(1, contentNo);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Download download = new Download();
        download.setContentNo(contentNo);
        download.setMemberNo(rs.getInt("mno"));
        download.setRegisterDate(rs.getString("rdt"));
        download.setViewCount(rs.getInt("vw_cnt"));
        download.setPath(rs.getString("path"));
        rs.close();
        return download;

      } else {
        rs.close();
        return null;
      }
    } finally {
      ds.returnConnection(con);
    }
  }

  public void update(Download download) throws Exception {
    Connection con = ds.getConnection();
    try (PreparedStatement stmt = con.prepareStatement(
        "update content co, download do set co.rdt=now(), do.path=? where co.cono=do.dnno and co.cono=?");) {
      stmt.setString(1, download.getPath());
      stmt.setInt(2, download.getContentNo());

      stmt.executeUpdate();

    } finally {
      ds.returnConnection(con);
    }
  }

  public void delete(int contentNo) throws Exception {
    Connection con = ds.getConnection();
    try (PreparedStatement stmt = con.prepareStatement("delete from download where dnno=?");) {

      stmt.setInt(1, contentNo);
      stmt.executeUpdate();

    } finally {
      ds.returnConnection(con);
    }
  }

  @Override
  public boolean exist(int contentNo) throws Exception {
    Connection con = ds.getConnection();
    try (PreparedStatement stmt = con.prepareStatement("select count(*) from download where dnno=?");) {

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
