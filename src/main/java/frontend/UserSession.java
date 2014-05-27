package frontend;

import java.util.Date;

public class UserSession {

    private String sessionId;
    private String userName;
    private String userMessage;
    private Long userId;
    private Date lastActivityTime;//???


    public UserSession(String id, String name, Long uid)
    {
        sessionId = id;
        userName = name;
        userMessage = "";
    }


    public UserSession(String id)
    {
        sessionId = id;
        userName = null;
        userId = null;
        userMessage = "";

    }
    public String getSessionId()
    {
        return sessionId;
    }


    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }


    public String getName()
    {
        return userName;
    }

    public void setName(String name)
    {
        userName = name;
    }


    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long id)
    {
        userId = id;
    }


    public Date getLastAction() {
        return lastActivityTime;
    }

    public void setLastAction(Date lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }
}
