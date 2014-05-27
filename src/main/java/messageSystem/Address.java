package messageSystem;

import java.util.concurrent.atomic.AtomicInteger;

public class Address {
    private static AtomicInteger lastAddress = new AtomicInteger();
    private final int abonentId;

    public Address(){
        this.abonentId = lastAddress.incrementAndGet();
    }

    public int hashCode(){
        return abonentId;
    }
}