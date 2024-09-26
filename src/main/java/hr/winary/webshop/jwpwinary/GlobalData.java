package hr.winary.webshop.jwpwinary;

import hr.winary.webshop.jwpwinary.model.Wine;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GlobalData {
    public static List<Wine> cart = new ArrayList<>();
    public static Map<Long, Integer> wineQuantities = new HashMap<>();

    public static void addWineToCart(Wine wine) {
        cart.add(wine);
        wineQuantities.put(wine.getId(), wineQuantities.getOrDefault(wine.getId(), 0) + 1);
    }

    public static void removeWineFromCart(Wine wine) {
        cart.remove(wine);
        if (wineQuantities.containsKey(wine.getId())) {
            int quantity = wineQuantities.get(wine.getId()) - 1;
            if (quantity <= 0) {
                wineQuantities.remove(wine.getId());
            } else {
                wineQuantities.put(wine.getId(), quantity);
            }
        }
    }
}
