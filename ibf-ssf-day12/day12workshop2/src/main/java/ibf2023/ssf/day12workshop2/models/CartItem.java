package ibf2023.ssf.day12workshop2.models;

public class CartItem {

   private String name;
   private Integer quantity;

   public String getName() { 
      return name; 
   }

   public void setName(String name) { 
      this.name = name; 
   }

   public Integer getQuantity() { 
      return quantity; 
   }

   public void setQuantity(Integer quantity) { 
      this.quantity = quantity; 
   }
   
   // name|quantity
   public static CartItem toItem(String str) {
      String[] fields = str.split("\\|");
      CartItem item = new CartItem();
      item.setName(fields[0]);
      item.setQuantity(Integer.parseInt(fields[1]));
      return item;
   }

   public String toItemString() {
      return "%s|%d".formatted(name, quantity);
   }
}
