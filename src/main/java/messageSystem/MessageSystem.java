package messageSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSystem {
    private Map<Address, ConcurrentLinkedQueue<Message>> messagesQueues =
            new HashMap<Address, ConcurrentLinkedQueue<Message>>();
    private AddressService addressService = new AddressService();

    public void addService(Subscriber subscriber){
        addressService.addService(subscriber);
        messagesQueues.put(subscriber.getAddress(), new ConcurrentLinkedQueue<Message>());
    }

    public void execForSubscriber(Subscriber subscriber){
        Queue<Message> messagesQueue = messagesQueues.get(subscriber.getAddress());
        //System.out.append(String.valueOf(subscriber.getAddress().hashCode())).append("\n");
        while (!messagesQueue.isEmpty()) {
            Message message = messagesQueue.poll();
            message.exec(subscriber);
        }
    }

    public void sendMessage(Message message){
        messagesQueues.get(message.getTarget()).add(message);
    }

    public AddressService getAddressService() {
        return addressService;
    }
}
