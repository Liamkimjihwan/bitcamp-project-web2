package bitcamp.java89.ems2.domain;

public class Tag extends Content {
  private static final long serialVersionUID = 1L;
  
  protected int tagNo;
  protected String tagName;
  
  public int getTagNo() {
    return tagNo;
  }
  public void setTagNo(int tagNo) {
    this.tagNo = tagNo;
  }
  public String getTagName() {
    return tagName;
  }
  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  
}
