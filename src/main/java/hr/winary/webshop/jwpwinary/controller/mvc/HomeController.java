package hr.winary.webshop.jwpwinary.controller.mvc;

import hr.winary.webshop.jwpwinary.GlobalData;
import hr.winary.webshop.jwpwinary.model.Purchase;
import hr.winary.webshop.jwpwinary.model.User;
import hr.winary.webshop.jwpwinary.repository.PurchaseRepository;
import hr.winary.webshop.jwpwinary.repository.UserRepository;
import hr.winary.webshop.jwpwinary.service.CategoryService;
import hr.winary.webshop.jwpwinary.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    WineService wineService;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping({"/", "/home"})
    public String home(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
         return "index";
     }

    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("wines", wineService.getAllWines());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable Integer id){
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("wines", wineService.getAllWinesByCategoryId(id));
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/viewwine/{id}")
    public String viewWine(Model model, @PathVariable Long id){
        model.addAttribute("wine", wineService.getWineById(id).get());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "viewWine";
    }


    @GetMapping("/user/mypurchaseHistory")
    public String getMyPurchaseHistory(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Get the username of the currently authenticated user
        User user = userRepository.findByUsername(username);  // Assuming your UserRepository has this method
        List<Purchase> purchases = purchaseRepository.findByUser(user);
        model.addAttribute("purchases", purchases);
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "purchaseHistoryForUser";
    }


}
