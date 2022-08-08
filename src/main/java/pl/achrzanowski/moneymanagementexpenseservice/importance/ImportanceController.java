package pl.achrzanowski.moneymanagementexpenseservice.importance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/importance")
public class ImportanceController {

    @Autowired
    private ImportanceService importanceService;

    @GetMapping
    public List<Importance> getAll(){
        List<Importance> importanceList = importanceService.findAll();
        if(importanceList.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No importance found");
        return importanceList;
    }

}
