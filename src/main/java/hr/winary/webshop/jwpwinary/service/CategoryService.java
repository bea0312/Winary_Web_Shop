package hr.winary.webshop.jwpwinary.service;

import hr.winary.webshop.jwpwinary.model.Category;
import hr.winary.webshop.jwpwinary.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    public Optional<Category> getCategoryById(Integer id){
        return categoryRepository.findById(id);
    }
    public void addCategory(Category category){
        categoryRepository.save(category);
    }
    public void removeCategoryById(Integer id){
        categoryRepository.deleteById(id);
    }

}
