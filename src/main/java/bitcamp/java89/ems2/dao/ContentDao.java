
package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Content;

public interface ContentDao {
  ArrayList<Content> getList() throws Exception;
  boolean exist(int contentNo) throws Exception;
  void insert(Content content) throws Exception;
  Content getOne(int contentNo) throws Exception;
  void update(Content content) throws Exception;
  void delete(int contentNo) throws Exception;
  void downdelete(int downNo) throws Exception;

}