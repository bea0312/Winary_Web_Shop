package hr.winary.webshop.jwpwinary.controller.mvc;

import hr.winary.webshop.jwpwinary.dto.WineDTO;
import hr.winary.webshop.jwpwinary.model.Category;
import hr.winary.webshop.jwpwinary.model.Purchase;
import hr.winary.webshop.jwpwinary.model.Wine;
import hr.winary.webshop.jwpwinary.repository.PurchaseRepository;
import hr.winary.webshop.jwpwinary.service.CategoryService;
import hr.winary.webshop.jwpwinary.service.UserLoginService;
import hr.winary.webshop.jwpwinary.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    public static  String uploadDir ="C:\\Users\\Isabelle\\Desktop\\VUA\\Semestar6\\JWP\\JWP-Winary";

    @Autowired
    CategoryService categoryService;

    @Autowired
    WineService wineService;

    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    PurchaseRepository purchaseRepository;


    @GetMapping("/admin")
    public String adminHome() {
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getAddCategories(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postAddCategories(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable Integer id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        } else {
            return "404";
        }
    }

    @GetMapping("/admin/wines")
    public String getAllWines(Model model){
        model.addAttribute("wines", wineService.getAllWines());
        return "wines";
    }

    @GetMapping("/admin/wines/add")
    public String getAddWines(Model model){
        model.addAttribute("wineDTO", new WineDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "winesAdd";
    }


    @PostMapping("/admin/wines/add")
    public String postAddWines(@ModelAttribute("wineDTO") WineDTO wineDTO,
                               @RequestParam("productImage") MultipartFile file,
                               @RequestParam("imageName")String imageName) throws IOException {
        Wine wine = new Wine();
        wine.setId(wineDTO.getId());
        wine.setName(wineDTO.getName());
        wine.setCategory(categoryService.getCategoryById(wineDTO.getCategoryId()).get());
        wine.setYearOfHarvest(wineDTO.getYearOfHarvest());
        wine.setLiters(wineDTO.getLiters());
        wine.setPrice(wineDTO.getPrice());
        wine.setDescription(wineDTO.getDescription());
        String imageUUID;
        if (!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        }else{
            imageUUID = imageName;
        }
        wine.setImageName(imageUUID);
        wineService.addWine(wine);
        return "redirect:/admin/wines";
    }

    @GetMapping("/admin/wines/delete/{id}")
    public String deleteWine(@PathVariable Long id) {
        wineService.removeWineById(id);
        return "redirect:/admin/wines";
    }

    @GetMapping("/admin/wines/update/{id}")
    public String updateWine(@PathVariable Long id, Model model){
        Wine wine = wineService.getWineById(id).get();
        WineDTO wineDTO = new WineDTO();
        wineDTO.setId(wine.getId());
        wineDTO.setName(wine.getName());
        wineDTO.setCategoryId(wine.getCategory().getId());
        wineDTO.setYearOfHarvest(wine.getYearOfHarvest());
        wineDTO.setLiters(wine.getLiters());
        wineDTO.setPrice(wine.getPrice());
        wineDTO.setDescription(wine.getDescription());
        wineDTO.setImageName(wine.getImageName());

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("wineDTO", wineDTO);

        return "winesAdd";
    }

    @GetMapping("/admin/userlogins")
    public String userLogins(Model model) {
        model.addAttribute("loginEntries", userLoginService.getAllLoginEntries());
        return "userLogins";
    }

    @GetMapping("/user/purchaseHistory")
    public String getPurchaseHistory(Model model) {
        List<Purchase> purchases = purchaseRepository.findAll();
        model.addAttribute("purchases", purchases);
        return "purchaseHistory";
    }

}
