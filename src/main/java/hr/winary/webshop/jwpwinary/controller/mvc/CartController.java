package hr.winary.webshop.jwpwinary.controller.mvc;

import hr.winary.webshop.jwpwinary.GlobalData;
import hr.winary.webshop.jwpwinary.model.Purchase;
import hr.winary.webshop.jwpwinary.model.User;
import hr.winary.webshop.jwpwinary.model.Wine;
import hr.winary.webshop.jwpwinary.repository.PurchaseRepository;
import hr.winary.webshop.jwpwinary.service.MyUserDetailService;
import hr.winary.webshop.jwpwinary.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class CartController {
    @Autowired
    WineService wineService;
    @Autowired
    MyUserDetailService userService;

    @Autowired
    PurchaseRepository purchaseRepository;

    @GetMapping("/addToCart/{id}")  //na ovo odmah staviti login, da mora bit authenticated
    public String addToCart(@PathVariable Long id){
        GlobalData.cart.add(wineService.getWineById(id).get());
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String getCart(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Wine::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String removeCartItem(@PathVariable int index){
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Wine::getPrice).sum());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "checkout";
    }

    @GetMapping("/order/complete")
    public String getCompleteOrder(Model model){
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Wine::getPrice).sum());
        return "orderConfirmation";
    }

    @GetMapping("/order/paybycash")
    public String completeOrder(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        double total = GlobalData.cart.stream().mapToDouble(Wine::getPrice).sum();

        for (Wine wine : GlobalData.cart) {
            Purchase purchase = new Purchase();
            purchase.setUser(user);
            purchase.setWine(wine);
            purchase.setPurchaseTime(LocalDateTime.now());
            purchase.setQuantity(1);
            purchase.setTotalPrice(wine.getPrice());
            purchase.setPaymentMethod("Cash");
            purchaseRepository.save(purchase);
        }

        GlobalData.cart.clear();
        model.addAttribute("total", total);
        return "redirect:/home";
    }

    @PostMapping("/order/paypal")
    public String completeOrderWithPayPal(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        double total = GlobalData.cart.stream().mapToDouble(Wine::getPrice).sum();

        for (Wine wine : GlobalData.cart) {
            Purchase purchase = new Purchase();
            purchase.setUser(user);
            purchase.setWine(wine);
            purchase.setPurchaseTime(LocalDateTime.now());
            purchase.setQuantity(1);
            purchase.setTotalPrice(wine.getPrice());
            purchase.setPaymentMethod("PayPal");
            purchaseRepository.save(purchase);
        }

        GlobalData.cart.clear();
        model.addAttribute("total", total);
        return "redirect:/home";
    }

    @GetMapping("/cart/increase/{index}")
    public String increaseCartItem(@PathVariable int index){
        if (index >= 0 && index < GlobalData.cart.size()) {
            Wine wine = GlobalData.cart.get(index);
            GlobalData.addWineToCart(wine);
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart/decrease/{index}")
    public String decreaseCartItem(@PathVariable int index){
        if (index >= 0 && index < GlobalData.cart.size()) {
            Wine wine = GlobalData.cart.get(index);
            GlobalData.removeWineFromCart(wine);
        }
        return "redirect:/cart";
    }


}
