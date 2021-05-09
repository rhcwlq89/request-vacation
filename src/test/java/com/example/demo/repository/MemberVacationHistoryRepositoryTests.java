package com.example.demo.repository;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MemberVacationHistoryRepositoryTests {

    @Autowired
    private MemberVacationHistoryRepository historyRepository;

//    List<VacationHistoryDto> findByNameAndYear(String name, String year);
//    List<MemberVacationHistory> findHistoryListByStatusCode(VacationStatusCode statusCode, LocalDate localDate);
//    boolean existsHistoryByVacationRequestDto(VacationRequestDto vacationRequestDto, String name);
//    Optional<MemberVacationHistory> findByHistoryIdAndRequestStatus(Long historyId, String requestStatus, String name);

}
