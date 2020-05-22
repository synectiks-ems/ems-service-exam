package com.synectiks.exam.service.impl;

import com.synectiks.exam.service.CompetitiveExamService;
import com.synectiks.exam.domain.CompetitiveExam;
import com.synectiks.exam.repository.CompetitiveExamRepository;
import com.synectiks.exam.service.dto.CompetitiveExamDTO;
import com.synectiks.exam.service.mapper.CompetitiveExamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CompetitiveExam}.
 */
@Service
@Transactional
public class CompetitiveExamServiceImpl implements CompetitiveExamService {

    private final Logger log = LoggerFactory.getLogger(CompetitiveExamServiceImpl.class);

    private final CompetitiveExamRepository competitiveExamRepository;

    private final CompetitiveExamMapper competitiveExamMapper;

    public CompetitiveExamServiceImpl(CompetitiveExamRepository competitiveExamRepository, CompetitiveExamMapper competitiveExamMapper) {
        this.competitiveExamRepository = competitiveExamRepository;
        this.competitiveExamMapper = competitiveExamMapper;
    }

    /**
     * Save a competitiveExam.
     *
     * @param competitiveExamDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompetitiveExamDTO save(CompetitiveExamDTO competitiveExamDTO) {
        log.debug("Request to save CompetitiveExam : {}", competitiveExamDTO);
        CompetitiveExam competitiveExam = competitiveExamMapper.toEntity(competitiveExamDTO);
        competitiveExam = competitiveExamRepository.save(competitiveExam);
        return competitiveExamMapper.toDto(competitiveExam);
    }

    /**
     * Get all the competitiveExams.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompetitiveExamDTO> findAll() {
        log.debug("Request to get all CompetitiveExams");
        return competitiveExamRepository.findAll().stream()
            .map(competitiveExamMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one competitiveExam by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompetitiveExamDTO> findOne(Long id) {
        log.debug("Request to get CompetitiveExam : {}", id);
        return competitiveExamRepository.findById(id)
            .map(competitiveExamMapper::toDto);
    }

    /**
     * Delete the competitiveExam by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompetitiveExam : {}", id);
        competitiveExamRepository.deleteById(id);
    }
}
