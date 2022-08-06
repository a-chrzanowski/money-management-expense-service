package pl.achrzanowski.moneymanagementexpenseservice.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    public List<ExpenseDTO> getAll(){
        List<Expense> expenses = expenseService.findAll();
        if(expenses.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expenses found");
        return expenseMapper.expensesToExpensesDTO(expenses);
    }

    @GetMapping("/{id}")
    public ExpenseDTO getById(@PathVariable String id){
        try {
            Optional<Expense> optionalExpense = expenseService.findById(id);
            if(optionalExpense.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expense with given id found");
            return expenseMapper.toExpenseDTO(optionalExpense.get());
        } catch (NumberFormatException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, id + " is not correct id");
        }
    }

    @PostMapping("/save")
    public void save(@RequestBody ExpenseDTO expenseDTO){
        try {
            Expense expense = expenseMapper.toExpense(expenseDTO);
            expenseService.save(expense);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving to database");
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id){
        try {
            expenseService.deleteById(id);
        } catch (NumberFormatException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, id + " is not correct id");
        } catch (EmptyResultDataAccessException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expense with id " + id + " exist");
        }
    }

}
