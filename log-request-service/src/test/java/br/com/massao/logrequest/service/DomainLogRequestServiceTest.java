package br.com.massao.logrequest.service;

import br.com.massao.logrequest.util.DateFormatterUtil;
import br.com.massao.logrequest.domain.DomainLogRequest;
import br.com.massao.logrequest.domain.NotFoundException;
import br.com.massao.logrequest.domain.repository.DomainLogRequestRepositoryPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DomainLogRequestServiceTest {

    //@MockBean
    @Mock
    DomainLogRequestRepositoryPort repositoryPort;

    @InjectMocks
    private DomainLogRequestService service;



    private LocalDateTime localDateTime = DateFormatterUtil.localDateTimeFrom("2021-07-17 01:01:01.001");
    private DomainLogRequest log1 = new DomainLogRequest(1L, localDateTime, "ip1", "request", (short) 200, "userAgent");
    private DomainLogRequest log2 = new DomainLogRequest(2L, localDateTime, "ip2", "request", (short) 200, "userAgent");


    @BeforeEach
    void setUp() {
        //repositoryPort = mock(DomainLogRequestRepositoryPort.class);
        //service = new DomainLogRequestService(repositoryPort);
    }

    /**
     * LIST TEST CASES
     */

    @Test
    void givenLogsWhenListThenReturnLogs() {
        // given
        //Page<DomainLogRequest> pageModel = new PageImpl<>(Arrays.asList(log1, log2));
        //given(repository.findAll(any(Pageable.class))).willReturn(pageModel);

        List<DomainLogRequest> pageModel = Arrays.asList(log1, log2);
        given(repositoryPort.listAll()).willReturn(pageModel);

        // when
        //Page<DomainLogRequest> result = service.list(pageModel.getPageable());
        List<DomainLogRequest> result = service.list();

        // then
        assertThat(result).isNotNull();
//        assertThat(result.getTotalElements()).isEqualTo(2);
//        assertThat(result.getContent().contains(log1)).isTrue();
//        assertThat(result.getContent().contains(log2)).isTrue();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.contains(log1)).isTrue();
        assertThat(result.contains(log2)).isTrue();
    }


    /**
     * FIND BY ID TEST CASES
     */

    @Test
    void givenLogsWhenFindByIdLogThenReturnLog() throws NotFoundException {
        // given
        given(repositoryPort.findById(log1.getId())).willReturn(Optional.of(log1));

        // when
        DomainLogRequest result = service.findById(log1.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(log1.getId());
    }

    @Test
    void givenLogNotFoundWhenFindByIdLogThenThrowsNotFoundException() {
        // given
        Long id = 9999999999999L;
        when(repositoryPort.findById(anyLong())).thenReturn(Optional.empty());

        // when/then
        Assertions.assertThatExceptionOfType(NotFoundException.class).isThrownBy(
                () -> service.findById(id));
    }


    /**
     * CREATE TEST CASES
     */

    @Test
    void givenLogWhenCreateLogThenReturnLog() {
        // given
        given(repositoryPort.create(log1)).willReturn(log1);

        // when
        DomainLogRequest result = service.save(log1);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(log1.getId());
    }


    /**
     * EDIT TEST CASES
     */

    @Test
    void givenLogWhenEditLogThenReturnLog() throws NotFoundException {
        // given
//        given(repositoryPort.findById(log1.getId())).willReturn(Optional.of(log1));
//        given(repositoryPort.create(log1)).willReturn(log1);

        given(repositoryPort.update(anyLong(), any(DomainLogRequest.class))).willReturn(log1);

        // when
        DomainLogRequest result = service.update(log1.getId(), log1);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(log1.getId());
//        verify(repositoryPort).findById(any());
//        verify(repositoryPort).create(any());
    }

    @Test
    void givenLogNotFoundWhenEditLogThenThrowsNotFoundException() throws NotFoundException {
        // given
        //given(repositoryPort.findById(log1.getId())).willReturn(Optional.empty());
        when(repositoryPort.update(anyLong(), any())).thenThrow(NotFoundException.class);


        // when/then
        Assertions.assertThatExceptionOfType(NotFoundException.class).isThrownBy(
                () -> service.update(log1.getId(), null));
    }


    /**
     * SEARCH TEST CASES
     */

//    @Test
//    void givenLogsWhenSearchThenReturnLogs() {
//        // given
//        //Page<DomainLogRequest> domains = new PageImpl<>(Arrays.asList(log1, log2));
//        //given(repository.findAll(any(Specification.class), any(Pageable.class))).willReturn(domains);
//
//        List<DomainLogRequest> domains = Arrays.asList(log1, log2);
//        given(repository.listAll()).willReturn(domains);
//
//        Specification<DomainLogRequest> spec = new Specification<DomainLogRequest>() {
//            @Override
//            public Predicate toPredicate(Root<DomainLogRequest> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                return null;
//            }
//        };
//
//        // when
//        Page<DomainLogRequest> result = service.searchByFilters(spec, domains.getPageable());
//
//        // then
//        assertThat(result).isNotNull();
//        assertThat(result.getTotalElements()).isEqualTo(2);
//        assertThat(result.getContent().contains(log1)).isTrue();
//        assertThat(result.getContent().contains(log2)).isTrue();
//    }


    /**
     * TestConfiguration guarantee this bean is only for test scope
     */
    @TestConfiguration
    static class LogRequestServiceTestContextConfiguration {
//        @Autowired
//        private JpaLogRequestRepository repo;

//        @Bean
//        LogRequestService service() {
//            return new LogRequestServiceImpl(new JpaLogRequestRepository());
//
//        }


//        @Bean
//        Validator validator() {
//            return new LocalValidatorFactoryBean();
//        }
    }
}