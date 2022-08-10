package pl.achrzanowski.moneymanagementexpenseservice.category;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryServiceTest {
    
    @MockBean
    private CategoryRepository categoryRepository;
    
    @Autowired
    private CategoryService categoryService;

    @Test
    public void when_repositoryIsEmpty_expect_emptyList(){
        List<Category> categoryList = categoryService.findAll();
        assertTrue(categoryList.isEmpty());
    }

    @Test
    public void when_repositoryIsEmpty_expect_notNull(){
        List<Category> categoryList = categoryService.findAll();
        assertNotNull(categoryList);
    }

    @Test
    public void when_repositoryIsNotEmpty_expect_notEmptyList(){
        Mockito.when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(new Category(), new Category()));

        List<Category> list = categoryService.findAll();
        assertFalse(list.isEmpty());
    }
}
