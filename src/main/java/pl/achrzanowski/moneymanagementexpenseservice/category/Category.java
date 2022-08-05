package pl.achrzanowski.moneymanagementexpenseservice.category;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Category {

    @Id
    private String name;

}
