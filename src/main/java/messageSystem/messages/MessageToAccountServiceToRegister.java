package messageSystem.messages;

import messageSystem.Address;
import messageSystem.Message;
import services.AccountService;

public class MessageToAccountServiceToRegister extends MessageToAccountService {
    private String login;
    private String pass;
    private String sessionId;

    public MessageToAccountServiceToRegister(Address creator, Address target, String login,
                                             String pass, String sessionId)
    {
        super(creator, target);
        this.login = login;
        this.pass = pass;
        this.sessionId = sessionId;
    }
    public void exec(AccountService accountService){
        long id = accountService.registerUser(login, pass);

        Message back = new MessageToFrontendUpdateUserId(getTarget(), getCreator(), sessionId, id);
        accountService.getMessageSystem().sendMessage(back);
    }
}
