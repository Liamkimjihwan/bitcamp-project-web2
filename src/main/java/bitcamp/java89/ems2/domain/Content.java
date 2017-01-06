

package bitcamp.java89.ems2.domain;

public class Content extends Member {
  private static final long serialVersionUID = 1L;
  protected int contentNo;
  protected String registerDate;
  protected int viewCount;
  
  public int getContentNo() {
    return contentNo;
  }
  public void setContentNo(int contentNo) {
    this.contentNo = contentNo;
  }
  public String getRegisterDate() {
    return registerDate;
  }
  public void setRegisterDate(String registerDate) {
    this.registerDate = registerDate;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }
  
 
  
}