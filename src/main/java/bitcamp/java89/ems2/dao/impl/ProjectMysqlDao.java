package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bitcamp.java89.ems2.dao.ProjectDao;
import bitcamp.java89.ems2.domain.Project;
import bitcamp.java89.ems2.util.DataSource;

public class ProjectMysqlDao implements ProjectDao {
  DataSource ds;
  
  public void setDataSource(DataSource ds) {
    this.ds = ds;
  }
  
  public ArrayList<Project> getList() throws Exception {
    ArrayList<Project> list = new ArrayList<>();
    Connection con = ds.getConnection();
    
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select titl,conts,sdt,edt,memb.name "
          + "from proj left outer join proj_memb on proj.pjno=proj_memb.pjno and proj_memb.rol='팀장'"
          + "left outer join memb on proj_memb.mno=memb.mno");
      ResultSet rs = stmt.executeQuery(); ){
      
      while (rs.next()) {
        Project project = new Project();
        project.setTitle(rs.getString("titl"));
//        project.setStartDate(rs.getString("sdt"));
//        project.setEndDate(rs.getString("edt"));
//        project.setContents(rs.getString("conts"));
        //project.setProjectNo(Integer.parseInt(rs.getString("pjno"))); //회원이름으로 변경 필요
        
        list.add(project);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }

  @Override
  public boolean exist(int projectNo) throws Exception {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void insert(Project project) throws Exception {
    // TODO Auto-generated method stub
    
  }
  
}