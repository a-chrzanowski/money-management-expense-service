package pl.achrzanowski.moneymanagementexpenseservice.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAll(){
        List<Category> categories = categoryService.findAll();
        if(categories.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No categories found");
        return categories;
    }
}
