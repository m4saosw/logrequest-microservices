package br.com.massao.logrequest.config;

import br.com.massao.logrequest.LogRequestServiceApplication;
import br.com.massao.logrequest.domain.repository.DomainLogRequestRepositoryPort;
import br.com.massao.logrequest.service.DomainLogRequestService;
import br.com.massao.logrequest.service.LogRequestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = LogRequestServiceApplication.class)
public class BeanConfiguration {

    @Bean
    LogRequestService logRequestService(final DomainLogRequestRepositoryPort repository) {
        return new DomainLogRequestService(repository);
    }
}
