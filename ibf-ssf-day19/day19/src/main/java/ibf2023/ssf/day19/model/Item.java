package ibf2023.ssf.day19.model;

public class Item {
    private String item;
    private int quantity;

    public String getItem() { 
        return item; 
    }

    public void setItem(String item) { 
        this.item = item; 
    }

    public int getQuantity() { 
        return quantity; 
    }

    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }

    @Override
    public String toString() {
        return "Item [item=" + item + ", quantity=" + quantity + "]";
    }
}
