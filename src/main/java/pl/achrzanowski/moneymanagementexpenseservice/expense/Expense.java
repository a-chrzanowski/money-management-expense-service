package pl.achrzanowski.moneymanagementexpenseservice.expense;

import lombok.Data;
import pl.achrzanowski.moneymanagementexpenseservice.category.Category;
import pl.achrzanowski.moneymanagementexpenseservice.importance.Importance;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Expense {

    @Id
    private Integer id;
    private String title;
    private String description;
    private BigDecimal amount;
    private LocalDate occurrenceDate;
    private LocalDateTime createdDate;
    @OneToOne
    @JoinColumn(name = "importance")
    private Importance importance;
    @OneToOne
    @JoinColumn(name = "category")
    private Category category;


}
