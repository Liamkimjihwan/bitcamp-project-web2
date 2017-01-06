package bitcamp.java89.ems2.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class MultipartUtill {
   public static Map<String, String> parse(HttpServletRequest request) throws Exception {
     int count = 0;
     HashMap<String, String> parse = new HashMap<>();
     
     if(!ServletFileUpload.isMultipartContent(request)) {
       throw new Exception("멀티파트 형식이 아닙니다.");
       
     }
     
     DiskFileItemFactory itemFactory = new DiskFileItemFactory();
     ServletFileUpload uploadParser = new ServletFileUpload(itemFactory);
     List<FileItem> items = uploadParser.parseRequest(request);
     
     for (FileItem item : items) {
       if (item.isFormField()) {
         parse.put(item.getFieldName(), item.getString("UTF-8"));
       } else {
         if (item.getSize() == 0) {
           continue; // 아래 실행하지말고 반복문으로 올라가라는 뜻. 사진이 없으면 실행 ㄴㄴ
         }
         String filename = System.currentTimeMillis() +  "_" + count++;
         item.write(new File(request.getServletContext().getRealPath("/upload/" + filename)));
         parse.put(item.getFieldName(), filename);
       }
     }
     return parse;
   }
}
