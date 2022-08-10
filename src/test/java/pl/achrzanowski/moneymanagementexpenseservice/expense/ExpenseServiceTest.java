package pl.achrzanowski.moneymanagementexpenseservice.expense;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExpenseServiceTest {

    @MockBean
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseService expenseService;

    @Test
    public void when_findAllAndRepositoryIsEmpty_expect_emptyList(){
        List<Expense> expenseList = expenseService.findAll();
        assertTrue(expenseList.isEmpty());
    }

    @Test
    public void when_findAllAndRepositoryIsEmpty_expect_notNull(){
        List<Expense> expenseList = expenseService.findAll();
        assertNotNull(expenseList);
    }

    @Test
    public void when_findAllAndRepositoryIsNotEmpty_expect_notEmptyList(){
        Mockito.when(expenseRepository.findAll())
                .thenReturn(List.of(new Expense(), new Expense()));

        List<Expense> list = expenseService.findAll();
        assertFalse(list.isEmpty());
    }

    @Test
    public void when_findByIdAndRepositoryIsEmpty_expect_notNull(){
        Optional<Expense> expense = expenseService.findById("1");
        assertNotNull(expense);
    }

    @Test
    public void when_findByIdAndRepositoryIsNotEmpty_expect_optionalWithValue(){
        Expense mockExpense = new Expense();
        mockExpense.setId(1);
        Mockito.when(expenseRepository.findById(1)).thenReturn(Optional.of(mockExpense));

        Optional<Expense> expense = expenseService.findById("1");
        assertTrue(expense.isPresent());
    }

    @Test
    public void when_findByIdWithWrongInputForId_expect_numberFormatException(){
        Exception exception = assertThrows(NumberFormatException.class,
                                            () -> expenseService.findById("test"));
        assertNotNull(exception);
    }

    @Test
    public void when_save_expect_expenseIsSavedInRepository(){
        Expense testExpense = new Expense();
        expenseService.save(testExpense);
        Mockito.verify(expenseRepository).save(Mockito.any(Expense.class));
    }

    @Test
    public void when_delete_expect_expenseIsDeletedFromRepository(){
        expenseService.deleteById("1");
        Mockito.verify(expenseRepository).deleteById(1);
    }

    @Test
    public void when_deleteWithWrongInputId_expect_numberFormatException() {
        Exception exception = assertThrows(NumberFormatException.class, () -> expenseService.deleteById("test"));
        assertNotNull(exception);
    }

}
