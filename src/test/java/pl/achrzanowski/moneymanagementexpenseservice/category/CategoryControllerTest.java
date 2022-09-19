package pl.achrzanowski.moneymanagementexpenseservice.category;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void when_getAllAndNotEmptyService_expect_statusOkAndNotEmptyBody() throws Exception{
        Category testCategory = new Category();
        testCategory.setName("test");
        Mockito.when(categoryService.findAll()).thenReturn(List.of(testCategory));
        this.mockMvc
                .perform(get("/api/category"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"" + testCategory.getName() + "\"}]"));
    }

    @Test
    public void when_getAllAndEmptyService_expect_statusNotFoundAndErrorMessage() throws Exception {
        this.mockMvc
                .perform(get("/api/category"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("No categories found", result.getResponse().getErrorMessage()));
    }


}
