package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Todo;

public interface TodoDao {
  ArrayList<Todo> getList() throws Exception;
  boolean exist(int sequence) throws Exception;
  boolean exist(String email) throws Exception;
  void insert(Todo todo) throws Exception;
  Todo getOne(int contentNo) throws Exception;
  void update(Todo todo) throws Exception;
  void delete(int cono) throws Exception;
}
