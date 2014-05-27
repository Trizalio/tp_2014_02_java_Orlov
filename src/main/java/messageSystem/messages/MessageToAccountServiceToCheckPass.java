package messageSystem.messages;

import messageSystem.Address;
import messageSystem.Message;
import services.AccountService;

public class MessageToAccountServiceToCheckPass extends MessageToAccountService {
    private String login;
    private String pass;
    private String sessionId;

    public MessageToAccountServiceToCheckPass(Address creator, Address target, String login,
                                              String pass, String sessionId)
    {
        super(creator, target);
        this.login = login;
        this.pass = pass;
        this.sessionId = sessionId;
        System.out.append(this.login).append(this.pass).append(this.sessionId).append("\n");
    }
    public void exec(AccountService accountService){
        long id = accountService.loginUser(login, pass);
        System.out.append(String.valueOf(id)).append("\n");

        Message back = new MessageToFrontendUpdateUserId(getTarget(), getCreator(), sessionId, id);
        accountService.getMessageSystem().sendMessage(back);
    }
}
