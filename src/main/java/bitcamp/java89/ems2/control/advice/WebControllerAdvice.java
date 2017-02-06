package bitcamp.java89.ems2.control.advice;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

// page 컨트롤러가 사전작업하기 전에 실행되는...
@ControllerAdvice
public class WebControllerAdvice {
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @InitBinder
  // 커스텀 에디터를 등록하는 메서드이다 라고 선언해주는 애노테이션
  public void initBinder(WebDataBinder binder) {
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
  }
}
