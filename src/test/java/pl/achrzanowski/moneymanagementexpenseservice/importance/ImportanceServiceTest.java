package pl.achrzanowski.moneymanagementexpenseservice.importance;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ImportanceServiceTest {

    @MockBean
    private ImportanceRepository importanceRepository;

    @Autowired
    private ImportanceService importanceService;

    @Test
    public void when_repositoryIsEmpty_expect_emptyList(){
        List<Importance> importanceList = importanceService.findAll();
        assertTrue(importanceList.isEmpty());
    }

    @Test
    public void when_repositoryIsEmpty_expect_notNull(){
        List<Importance> importanceList = importanceService.findAll();
        assertNotNull(importanceList);
    }

    @Test
    public void when_repositoryIsNotEmpty_expect_notEmptyList(){
        Mockito.when(importanceRepository.findAll())
                .thenReturn(Arrays.asList(new Importance(), new Importance()));

        List<Importance> list = importanceService.findAll();
        assertFalse(list.isEmpty());
    }

}
