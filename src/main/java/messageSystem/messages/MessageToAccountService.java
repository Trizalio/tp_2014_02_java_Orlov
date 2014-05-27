package messageSystem.messages;

import services.AccountService;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.Subscriber;

public abstract class MessageToAccountService extends Message {
    public MessageToAccountService(Address creator, Address target){
        super(creator, target);
    }

    public void exec(Subscriber subscriber){
        if(subscriber instanceof AccountService){
            exec( (AccountService) subscriber);
        }
    }

    public abstract void exec(AccountService accountService);
}
