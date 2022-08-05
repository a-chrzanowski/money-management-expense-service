package pl.achrzanowski.moneymanagementexpenseservice.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> findAll(){
        return expenseRepository.findAll();
    }

    public Optional<Expense> findById(String id){
        return expenseRepository.findById(Integer.parseInt(id));
    }

}
