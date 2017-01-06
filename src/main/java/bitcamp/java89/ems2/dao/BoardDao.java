
package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Board;

public interface BoardDao {

  Board getOne(int memberNo) throws Exception;
  boolean exist(int contentsNo) throws Exception ;
  void update(Board board) throws Exception ;
  void updateViewCount(int viewCount, int contentNo) throws Exception;
  void insert(Board board) throws Exception;
  ArrayList<Board> getList() throws Exception;
  void delete(int contentsNo) throws Exception;

}
