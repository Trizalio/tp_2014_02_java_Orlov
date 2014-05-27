package messageSystem.messages;

import frontend.Frontend;
import messageSystem.Address;

public class MessageToFrontendUpdateUserId extends MessageToFrontend{

    private String sessionId;
    private long userId;

    public MessageToFrontendUpdateUserId(Address from, Address to, String sessionId, long userId)
    {
        super(from, to);
        this.sessionId = sessionId;
        this.userId = userId;
        System.out.append(this.sessionId).append(String.valueOf(this.userId))
                .append(this.sessionId).append("\n");
    }

    public void exec(Frontend frontend)
    {
        System.out.append(this.sessionId).append(String.valueOf(this.userId))
                .append(this.sessionId).append("\n");
        frontend.updateUserId(sessionId, userId);
    }
}
