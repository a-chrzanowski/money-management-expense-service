package pl.achrzanowski.moneymanagementexpenseservice.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> findAllByOwner(String owner){
        return expenseRepository.findAllByOwner(owner);
    }

    public Optional<Expense> findById(String id){
        return expenseRepository.findById(Integer.parseInt(id));
    }

    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void deleteById(String id){
        expenseRepository.deleteById(Integer.parseInt(id));
    }
}
