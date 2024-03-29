package ibf2023.ssf.day12workshop2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import ibf2023.ssf.day12workshop2.models.CartItem;

public class Utils {
    // name|quantity,name|quantity,...
    public static List<CartItem> deserializeCart(String cartStr) {

        if (cartStr.trim().length() <= 0)
        return new LinkedList<>();

        List<String> list = Arrays.asList(cartStr.split(","));
        // name|quantity,name|quantity,...
        List<CartItem> itemList = list.stream()
        // name|quantity
        //map(str -> Item.toItem(str))
        .map(CartItem::toItem)
        .toList();
        return new LinkedList<>(itemList);
    }

    public static String serializeCart(List<CartItem> cart) {
        return cart.stream()
        .map(item -> item.toItemString())
        .collect(Collectors.joining(","));
    }
}
