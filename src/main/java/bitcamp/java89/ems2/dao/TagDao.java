package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Content;
import bitcamp.java89.ems2.domain.Tag;

public interface TagDao {
  void insert(Tag tag) throws Exception;
  ArrayList<Tag> getList() throws Exception;
  ArrayList<Tag> getOne(String tagName) throws Exception;
  ArrayList<Tag> getOne(int ContentNo) throws Exception;
  void update(Tag tag) throws Exception;
  boolean exist(int contentNo) throws Exception;
  String getContent(int contentNo) throws Exception;
  public void delete(Content content) throws Exception;
}
