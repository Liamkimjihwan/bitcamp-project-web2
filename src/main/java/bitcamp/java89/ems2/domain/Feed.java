package bitcamp.java89.ems2.domain;

public class Feed extends Content {
  private static final long serialVersionUID = 1L;
  
  protected int feedNo;
  protected String contents;
  
  
  public int getFeedNo() {
    return feedNo;
  }

  public void setFeedNo(int feedNo) {
    this.feedNo = feedNo;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }
  
}
