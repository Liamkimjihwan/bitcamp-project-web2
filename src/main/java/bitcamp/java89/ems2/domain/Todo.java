package bitcamp.java89.ems2.domain;

public class Todo extends Project {
  private static final long serialVersionUID = 1L;
 
  protected int todoNo;
  protected int sequence; // 순서
  protected String tdcontents; // 내용
  protected String state; // 상태
  protected String stateDate; // 상태설정일
  

  public int getTodoNo() {
    return todoNo;
  }
  public void setTodoNo(int todoNo) {
    this.todoNo = todoNo;
  }
  public int getSequence() {
    return sequence;
  }
  public void setSequence(int sequence) {
    this.sequence = sequence;
  }
  public String getTdContents() {
    return tdcontents;
  }
  public void setTdContents(String tdcontents) {
    this.tdcontents = tdcontents;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getStateDate() {
    return stateDate;
  }
  public void setStateDate(String stateDate) {
    this.stateDate = stateDate;
  }
  
 
 
 
}
