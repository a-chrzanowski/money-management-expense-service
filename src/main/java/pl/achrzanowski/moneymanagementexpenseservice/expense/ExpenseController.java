package pl.achrzanowski.moneymanagementexpenseservice.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseMapper expenseMapper;

    @GetMapping("/all")
    public List<ExpenseDTO> getAllExpenses(){
        List<Expense> expenses = expenseService.findAll();
        if(expenses.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expenses found");
        return expenseMapper.expensesToExpensesDTO(expenses);
    }

    @GetMapping("/{id}")
    public ExpenseDTO getExpenseById(@PathVariable String id){
        Optional<Expense> optionalExpense = expenseService.findById(id);
        if(optionalExpense.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expense with given id found", null);
        return expenseMapper.toExpenseDTO(optionalExpense.get());
    }



}
