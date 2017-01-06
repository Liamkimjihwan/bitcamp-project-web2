package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Download;

public interface DownloadDao {
  public ArrayList<Download> getList() throws Exception;
  public Download getOne(int contentNo) throws Exception;
  public boolean exist(int contentNo) throws Exception;
  public void insert(Download download) throws Exception;
  public void update(Download download) throws Exception;
  public void delete(int contentNo) throws Exception;
}
