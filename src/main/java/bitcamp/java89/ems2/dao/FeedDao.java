package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Feed;

public interface FeedDao {
  ArrayList<Feed> getList() throws Exception;
  void insert(Feed feed) throws Exception;
  Feed getOne(int contentsNo) throws Exception;
  void delete(int contentsNo) throws Exception;
  void updateCount(int viewCount, int contentNo) throws Exception;
  void update(Feed feed) throws Exception;
}