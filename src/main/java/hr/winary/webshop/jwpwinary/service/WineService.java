package hr.winary.webshop.jwpwinary.service;

import hr.winary.webshop.jwpwinary.model.Category;
import hr.winary.webshop.jwpwinary.model.Wine;
import hr.winary.webshop.jwpwinary.repository.CategoryRepository;
import hr.winary.webshop.jwpwinary.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WineService {
    @Autowired
    WineRepository wineRepository;

    public List<Wine> getAllWines(){
        return wineRepository.findAll();
    }
    public Optional<Wine> getWineById(Long id){
        return wineRepository.findById(id);
    }
    public void addWine(Wine wine){
        wineRepository.save(wine);
    }
    public void removeWineById(Long id){
        wineRepository.deleteById(id);
    }
    public List<Wine> getAllWinesByCategoryId(Integer id){
        return wineRepository.findAllByCategoryId(id);
    }
}
