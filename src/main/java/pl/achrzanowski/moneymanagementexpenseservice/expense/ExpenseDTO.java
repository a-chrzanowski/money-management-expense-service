package pl.achrzanowski.moneymanagementexpenseservice.expense;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Data
public class ExpenseDTO {

    private Integer id;
    private String title;
    private String description;
    private String amount;
    private String category;
    private String importance;
    private LocalDate occurrenceDate;
    private LocalDateTime createdDate;

}
