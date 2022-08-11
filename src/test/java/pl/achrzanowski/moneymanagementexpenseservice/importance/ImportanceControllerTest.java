package pl.achrzanowski.moneymanagementexpenseservice.importance;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImportanceController.class)
public class ImportanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImportanceService importanceService;

    @Test
    public void when_getAllAndNotEmptyService_expect_statusOkAndNotEmptyBody() throws Exception{
        Importance testImportance = new Importance();
        testImportance.setName("test");
        Mockito.when(importanceService.findAll()).thenReturn(List.of(testImportance));
        this.mockMvc
                .perform(get("/api/importance"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"" + testImportance.getName() + "\"}]"));
    }

    @Test
    public void when_getAllAndEmptyService_expect_statusNotFoundAndReason() throws Exception {
        this.mockMvc
                .perform(get("/api/importance"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("No importance found", result.getResponse().getErrorMessage()));
    }

}
