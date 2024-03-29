package ibf2023.ssf.day13.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Item {

   // <input type="text" name="name">
   @NotEmpty(message = "Please enter your name")
   @Size(min = 2, max = 64, message = "Your name must between 2 to 64 characters")
   private String name;

   // <input type="number" name="quantity">
   @Min(value = 1, message = "You must order at least 1 item")
   @NotNull(message = "You must order at least 1 item")
   private Integer quantity;

   public Item() {
   }

   public Item( String name, Integer quantity) {
      this.name = name;
      this.quantity = quantity;
   }

   // name
   public String getName() { 
      return this.name; 
   }

   public void setName(String name) { 
      this.name = name; 
   }

   // quantity
   public Integer getQuantity() { 
      return quantity; 
   }

   public void setQuantity(Integer quantity) { 
      this.quantity = quantity; 
   }

   @Override
   public String toString() {
      return "Item [name=" + name + ", quantity=" + quantity + "]";
   }

}