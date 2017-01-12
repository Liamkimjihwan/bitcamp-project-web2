package bitcamp.java89.ems2.control;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Controller
public class SystemInfoControl {
  @Autowired ServletContext servletContext;
  
  @RequestMapping("/system/beansInfo")
  public void beansInfo(Model model) {
    // Model model 빈 바구니 = jsp에 전달할 데이터를 위해서 빈 바구니가 필요함.
    WebApplicationContext appContext = 
        WebApplicationContextUtils.getWebApplicationContext(servletContext);
    
    ArrayList<String> beanClassNames = new ArrayList<>(); 
    String[] names = appContext.getBeanDefinitionNames();
    
    for (String name : names) {
      beanClassNames.add(appContext.getBean(name).getClass().getName());
      // 스프링 ioC 컨트롤에서  appContext.getBean(name) 이 name으로 저장된 빈을 찾아 클래스 이름을 꺼낸다.
    }
    
    model.addAttribute("beanClassNames", beanClassNames);
  }

}
