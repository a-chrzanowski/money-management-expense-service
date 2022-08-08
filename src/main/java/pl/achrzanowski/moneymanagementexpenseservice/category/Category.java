package pl.achrzanowski.moneymanagementexpenseservice.category;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Category {

    @Id
    private String name;

}
