package messageSystem;

public abstract class Message {
    private Address creator;
    private  Address target;
    public Message(Address creator, Address target){
        this.creator = creator;
        this.target = target;
    }
    public abstract void exec(Subscriber subscriber);

    public Address getTarget() {
        return target;
    }

    public Address getCreator() {
        return creator;
    }
}
