package pl.achrzanowski.moneymanagementexpenseservice.expense;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({ExpenseController.class, ExpenseMapper.class})
@AutoConfigureMockMvc(addFilters = false)
public class ExpenseControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ExpenseService expenseService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String testOwner = "testOwner";

    @Test
    public void when_getAllAndNotEmptyService_expect_statusOkAndNotEmptyBody() throws Exception{
        Expense testExpense = new Expense();
        testExpense.setTitle("test");
        Mockito.when(expenseService.findAllByOwner(testOwner)).thenReturn(List.of(testExpense));
        this.mockMvc
                .perform(get("/api/expense?owner=" + testOwner))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"title\":\"" + testExpense.getTitle() + "\"}]"));
    }

    @Test
    public void when_getAllAndEmptyService_expect_statusNotFoundAndErrorMessage() throws Exception {
        this.mockMvc
                .perform(get("/api/expense?owner=" + testOwner))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("No expenses found", result.getResponse().getErrorMessage()));
    }

    @Test
    public void when_getByIdAndNotEmptyService_expect_statusOkAndNotEmptyResponse() throws Exception {
        Expense testExpense = new Expense();
        testExpense.setId(1);
        testExpense.setTitle("test");
        Mockito.when(expenseService.findById("1")).thenReturn(Optional.of(testExpense));
        this.mockMvc
                .perform(get("/api/expense/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\": 1, \"title\": \"test\"}"));
    }

    @Test
    public void when_getByIdAndEmptyService_expect_statusNotFoundAndErrorMessage() throws Exception {
        this.mockMvc
                .perform(get("/api/expense/{id}", "1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("No expense with given id found", result.getResponse().getErrorMessage()));
    }

    @Test
    public void when_getByIdWithIncorrectId_expect_statusBadRequestAndErrorMessage() throws Exception {
        String incorrectId = "test";
        Mockito.when(expenseService.findById(incorrectId)).thenThrow(new NumberFormatException());
        this.mockMvc
                .perform(get("/api/expense/{id}", incorrectId))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(incorrectId + " is not correct id", result.getResponse().getErrorMessage()));
    }

    @Test
    public void when_saveWithCorrectBody_expect_statusCreatedAndLocationHeader() throws Exception {
        Expense testExpense = new Expense();
        testExpense.setTitle("test");
        testExpense.setId(1);
        Mockito.when(expenseService.save(Mockito.any(Expense.class))).thenReturn(testExpense);

        String expenseJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(testExpense);
        this.mockMvc
                .perform(post("/api/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expenseJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("LOCATION", "http://localhost/api/expense/1"));
    }

    @Test
    public void when_saveWithIncorrectBody_expect_statusInternalServerErrorAndErrorMessage() throws Exception {
        this.mockMvc
                .perform(post("/api/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"test\": \"test\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertEquals("Error while saving to the database",
                                                        result.getResponse().getErrorMessage()));
    }

    @Test
    public void when_saveWithNoRequestBody_expect_statusBadRequestAndErrorMessage() throws Exception{
        this.mockMvc
                .perform(post("/api/expense")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Required request body is missing",
                                                        result.getResponse().getErrorMessage()));
    }
    
}












