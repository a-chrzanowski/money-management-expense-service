package pl.achrzanowski.moneymanagementexpenseservice.importance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportanceRepository extends JpaRepository<Importance, String> {
}
