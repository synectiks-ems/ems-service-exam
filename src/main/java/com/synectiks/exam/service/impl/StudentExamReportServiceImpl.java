package com.synectiks.exam.service.impl;

import com.synectiks.exam.service.StudentExamReportService;
import com.synectiks.exam.domain.StudentExamReport;
import com.synectiks.exam.repository.StudentExamReportRepository;
import com.synectiks.exam.service.dto.StudentExamReportDTO;
import com.synectiks.exam.service.mapper.StudentExamReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link StudentExamReport}.
 */
@Service
@Transactional
public class StudentExamReportServiceImpl implements StudentExamReportService {

    private final Logger log = LoggerFactory.getLogger(StudentExamReportServiceImpl.class);

    private final StudentExamReportRepository studentExamReportRepository;

    private final StudentExamReportMapper studentExamReportMapper;

    public StudentExamReportServiceImpl(StudentExamReportRepository studentExamReportRepository, StudentExamReportMapper studentExamReportMapper) {
        this.studentExamReportRepository = studentExamReportRepository;
        this.studentExamReportMapper = studentExamReportMapper;
    }

    /**
     * Save a studentExamReport.
     *
     * @param studentExamReportDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StudentExamReportDTO save(StudentExamReportDTO studentExamReportDTO) {
        log.debug("Request to save StudentExamReport : {}", studentExamReportDTO);
        StudentExamReport studentExamReport = studentExamReportMapper.toEntity(studentExamReportDTO);
        studentExamReport = studentExamReportRepository.save(studentExamReport);
        return studentExamReportMapper.toDto(studentExamReport);
    }

    /**
     * Get all the studentExamReports.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentExamReportDTO> findAll() {
        log.debug("Request to get all StudentExamReports");
        return studentExamReportRepository.findAll().stream()
            .map(studentExamReportMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one studentExamReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StudentExamReportDTO> findOne(Long id) {
        log.debug("Request to get StudentExamReport : {}", id);
        return studentExamReportRepository.findById(id)
            .map(studentExamReportMapper::toDto);
    }

    /**
     * Delete the studentExamReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentExamReport : {}", id);
        studentExamReportRepository.deleteById(id);
    }
}
