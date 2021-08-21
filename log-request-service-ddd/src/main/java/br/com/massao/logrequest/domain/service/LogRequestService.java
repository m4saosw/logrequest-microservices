package br.com.massao.logrequest.domain.service;

import br.com.massao.logrequest.domain.DomainLogRequest;
import br.com.massao.logrequest.domain.NotFoundException;
import br.com.massao.logrequest.infrastructure.model.LogRequestModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public interface LogRequestService {

    /**
     * List all logs
     *
     * @param pageable
     * @return
     */
    Page<LogRequestModel> list(Pageable pageable);


    /**
     * Find a log by id
     *
     * @param id
     * @return
     * @throws NotFoundException
     */
    DomainLogRequest findById(Long id) throws NotFoundException;


    /**
     * Save a new log
     *
     * @param model
     * @return
     */
    DomainLogRequest save(DomainLogRequest domain);


    /**
     * Update an existing log
     *
     * @param id
     * @param newLog
     * @return
     * @throws NotFoundException
     */
    LogRequestModel update(Long id, LogRequestModel newLog) throws NotFoundException;


    /**
     * Search logs by filters
     *
     * @param spec
     * @param pageable
     * @return
     */
    Page<LogRequestModel> searchByFilters(Specification<LogRequestModel> spec, Pageable pageable);
}
