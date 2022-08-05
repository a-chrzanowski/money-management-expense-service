package pl.achrzanowski.moneymanagementexpenseservice.importance;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "importance")
public class Importance {

    @Id
    private String name;
    private Float weight;

}
