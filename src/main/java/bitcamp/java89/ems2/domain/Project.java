package bitcamp.java89.ems2.domain;

public class Project extends Content {
  private static final long serialVersionUID = 1L;
  
  protected int projectNo;
  protected String title;
  protected String conttents;
  protected String startDT;
  protected String endDT;
  public int getProjectNo() {
    return projectNo;
  }
  public void setProjectNo(int projectNo) {
    this.projectNo = projectNo;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getConttents() {
    return conttents;
  }
  public void setConttents(String conttents) {
    this.conttents = conttents;
  }
  public String getStartDT() {
    return startDT;
  }
  public void setStartDT(String startDT) {
    this.startDT = startDT;
  }
  public String getEndDT() {
    return endDT;
  }
  public void setEndDT(String endDT) {
    this.endDT = endDT;
  }
  
  
  
  
}
