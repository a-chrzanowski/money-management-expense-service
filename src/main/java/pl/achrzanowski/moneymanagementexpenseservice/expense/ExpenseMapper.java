package pl.achrzanowski.moneymanagementexpenseservice.expense;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "importance.name", target = "importance")
    ExpenseDTO toExpenseDTO(Expense expense);

    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "importance.name", target = "importance")
    List<ExpenseDTO> expensesToExpensesDTO(List<Expense> expenses);

    @Mapping(source = "category", target = "category.name")
    @Mapping(source = "importance", target = "importance.name")
    Expense toExpense(ExpenseDTO expenseDTO);

}
