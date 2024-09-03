package com.kinginsai.CRM.intercepertor;

import java.util.Date;

public class Session {

     private String sessionId;
     private String usersName;
     private int maxInactiveInterval;
     private Date createTime;

      public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
      }

      public void setUserName(String usersName) {
        this.usersName = usersName;
      }

      public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
      }

      public void setCreateTime(Date createTime) {
        this.createTime = createTime;
      }

      public String getSessionId() {
        return sessionId;
      }

      public String getUsersName() {
        return usersName;
      }

      public int getMaxInactiveInterval() {
        return maxInactiveInterval;
      }

      public Date getCreateTime() {
        return createTime;
      }

      public Session() {
        }

	
    public Session(String sessionId, String usersName, int maxInactiveInterval, Date createTime) {
        this.sessionId = sessionId;
        this.usersName = usersName;
        this.maxInactiveInterval = maxInactiveInterval;
        this.createTime = createTime;
    }

}


