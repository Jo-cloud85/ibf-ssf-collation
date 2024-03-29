package ibf2023.ssf.day19.model;

import java.util.LinkedList;
import java.util.List;

public class Order {

    private final String orderId;
    private String name;
    private String email;
    private boolean rush;
    private String comments;
    private List<Item> items = new LinkedList<>();

    public Order(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() { 
        return this.orderId; 
    }

    public String getName() { 
        return name; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public String getEmail() { 
        return email; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    public boolean isRush() { 
        return rush;
    }

    public void setRush(boolean rush) { 
        this.rush = rush; 
    }

    public String getComments() { 
        return comments; 
    }

    public void setComments(String comments) { 
        this.comments = comments; 
    }

    public List<Item> getItems() { 
        return items; 
    }

    public void setItems(List<Item> items) { 
        this.items = items; 
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        return "Order [name=" + name + ", email=" + email + ", rush=" + rush + ", comments=" + comments + "]";
    }
}
