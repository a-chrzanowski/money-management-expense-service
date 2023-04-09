package pl.achrzanowski.moneymanagementexpenseservice.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseMapper expenseMapper;

    @GetMapping
    public List<ExpenseDTO> getAll(@RequestParam String owner){
        List<Expense> expenses = expenseService.findAllByOwner(owner);
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

    @PostMapping
    public ResponseEntity<ExpenseDTO> save(@RequestBody ExpenseDTO expenseDTO){
        try {
            Expense expense = expenseMapper.toExpense(expenseDTO);
            Expense savedExpense = expenseService.save(expense);
            String location = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedExpense.getId())
                            .toUriString();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header(HttpHeaders.LOCATION, location)
                    .body(expenseMapper.toExpenseDTO(savedExpense));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving to the database");
        }
    }

    @DeleteMapping
    public void delete(@RequestParam String id){
        try {
            expenseService.deleteById(id);
        } catch (NumberFormatException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, id + " is not correct id");
        } catch (EmptyResultDataAccessException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expense with id " + id + " exist");
        }
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public void handleHttpMessageNotReadableException(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                "Required request body is missing");
    }

}
