package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Project;
import bitcamp.java89.ems2.domain.Todo;

public interface ProjectDao {
//  public ArrayList<Project> getList() throws Exception;
  public boolean exist(int projectNo) throws Exception;
//  public boolean exist(String email) throws Exception;
  public void insert(Project project) throws Exception;
//  public Todo getOne(int memberNo) throws Exception;
//  public void update(Project project) throws Exception;
//  public void delete(int memberNo) throws Exception;
}
