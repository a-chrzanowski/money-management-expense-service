package pl.achrzanowski.moneymanagementexpenseservice.importance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportanceService {

    @Autowired
    private ImportanceRepository importanceRepository;

    public List<Importance> findAll(){
        return importanceRepository.findAll();
    }
}
