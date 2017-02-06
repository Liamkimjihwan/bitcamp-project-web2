package bitcamp.java89.ems2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bitcamp.java89.ems2.domain.Teacher;

@Service
public interface TeacherService {
  List<Teacher> getList() throws Exception;
  Teacher getDetail(int no) throws Exception;
  int add(Teacher teacher) throws Exception;
  int addWithPhoto(Teacher teacher) throws Exception;
  int delete(int no) throws Exception;
  int deleteWithPhoto(int no) throws Exception;
  int update(Teacher teacher) throws Exception;
}
