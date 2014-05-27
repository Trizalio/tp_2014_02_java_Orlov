package messageSystem;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AddressService {
    private Map<Class, List<Address>> servicesLists = new ConcurrentHashMap<>();
    private Map<Class, Iterator<Address>> iteratorsLists = new ConcurrentHashMap<>();

    public Address getService(Class clazz){
        Iterator<Address> iterator = iteratorsLists.get(clazz);

        if(!iterator.hasNext())
            iterator = servicesLists.get(clazz).iterator();

        return iterator.next();

    }
    public void addService(Subscriber subscriber){
        if(servicesLists.get(subscriber.getClass()) == null){
            servicesLists.put(subscriber.getClass(), new CopyOnWriteArrayList<Address>());
            iteratorsLists.put(subscriber.getClass(), servicesLists.get(subscriber.getClass()).iterator());
        }
        servicesLists.get(subscriber.getClass()).add(subscriber.getAddress());
    }
}
