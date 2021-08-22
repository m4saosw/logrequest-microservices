package br.com.massao.logrequest.domain.service;

import br.com.massao.logrequest.domain.DomainLogRequest;
import br.com.massao.logrequest.domain.NotFoundException;
import br.com.massao.logrequest.domain.repository.DomainLogRequestRepositoryPort;
import br.com.massao.logrequest.infrastructure.model.LogRequestModel;
import br.com.massao.logrequest.infrastructure.repository.LogRequestRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogRequestServiceImpl implements LogRequestService {
    // TODO - remover apos a migracao
    @Autowired
    private LogRequestRepository repository;

    private DomainLogRequestRepositoryPort repositoryPort;

    public LogRequestServiceImpl(DomainLogRequestRepositoryPort repository) {
        this.repositoryPort = repository;
    }



    /**
     * List all logs
     *
     * @param pageable
     * @return
     */
    @Override
    @HystrixCommand(threadPoolKey = "largeQueryThreadPool")
    public Page<DomainLogRequest> list(Pageable pageable) {
        log.debug("list pageable");

        return repositoryPort.listAll(pageable);
    }


    /**
     * Find a log by id
     *
     * @param id
     * @return
     * @throws NotFoundException
     */
    @Override
    @HystrixCommand(threadPoolKey = "queryThreadPool")
    public DomainLogRequest findById(Long id) throws NotFoundException {
        log.debug("findById id={}", id);

        return repositoryPort.findById(id).orElseThrow(() -> new NotFoundException());
    }


    /**
     * Save a new log
     *
     * @param domain
     * @return
     */
    @Override
    @HystrixCommand(threadPoolKey = "commandThreadPool")
    public DomainLogRequest save(DomainLogRequest domain) {
        log.debug("save domain={}", domain);

        return repositoryPort.create(domain);
    }


    /**
     * Update an existing log
     *
     * @param id
     * @param newLog
     * @return
     * @throws NotFoundException
     */
    @Override
    @HystrixCommand(threadPoolKey = "commandThreadPool")
    public DomainLogRequest update(Long id, DomainLogRequest newLog) throws NotFoundException {
        log.debug("update id={} new domain={}", id, newLog);

        return repositoryPort.update(id, newLog);
    }


    /**
     * Search logs by filters
     *
     * @param spec
     * @param pageable
     * @return
     */
    @Override
    @HystrixCommand(threadPoolKey = "largeQueryThreadPool")
    public Page<LogRequestModel> searchByFilters(Specification<LogRequestModel> spec, Pageable pageable) {
        log.debug("list pageable with filters");
        return repository.findAll(spec, pageable);
    }
}
