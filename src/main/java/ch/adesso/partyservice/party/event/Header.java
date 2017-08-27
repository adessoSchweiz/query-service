package ch.adesso.partyservice.party.event;

import java.util.HashMap;
import java.util.Map;

import org.apache.avro.reflect.Nullable;

/**
 * Created by tom on 23.06.17.
 */
public class Header {
    @Nullable
    private Map<String, Object> properties;

    public Header() {
        properties = new HashMap<String, Object>();
    }

    public void addProperty(String name, Object value) {
        properties.put(name, value);
    }

    public void removeProperty(String name) {
        properties.remove(name);
    }
}
