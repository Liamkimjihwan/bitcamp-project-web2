package bitcamp.java89.ems2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bitcamp.java89.ems2.domain.Manager;

@Service
public interface ManagerService {
  List<Manager> getList() throws Exception;
  Manager getDetail(int no) throws Exception;
  int add(Manager manager) throws Exception;
  int delete(int no) throws Exception;
  int update(Manager manager) throws Exception;
}
