package bitcamp.java89.ems2.dao.impl;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Project;

public interface ProjectDao {
  ArrayList<Project> getList() throws Exception;
}
