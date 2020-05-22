package com.synectiks.exam.web.rest;

import com.synectiks.exam.ExamApp;
import com.synectiks.exam.domain.StudentExamReport;
import com.synectiks.exam.repository.StudentExamReportRepository;
import com.synectiks.exam.service.StudentExamReportService;
import com.synectiks.exam.service.dto.StudentExamReportDTO;
import com.synectiks.exam.service.mapper.StudentExamReportMapper;
import com.synectiks.exam.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.synectiks.exam.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StudentExamReportResource} REST controller.
 */
@SpringBootTest(classes = ExamApp.class)
public class StudentExamReportResourceIT {

    private static final Integer DEFAULT_MARKS_OBTAINED = 1;
    private static final Integer UPDATED_MARKS_OBTAINED = 2;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Integer DEFAULT_G_OP = 1;
    private static final Integer UPDATED_G_OP = 2;

    private static final Long DEFAULT_SECTION_ID = 1L;
    private static final Long UPDATED_SECTION_ID = 2L;

    private static final Long DEFAULT_SUBJECT_ID = 1L;
    private static final Long UPDATED_SUBJECT_ID = 2L;

    private static final Long DEFAULT_DEPARTMENT_ID = 1L;
    private static final Long UPDATED_DEPARTMENT_ID = 2L;

    private static final Long DEFAULT_BATCH_ID = 1L;
    private static final Long UPDATED_BATCH_ID = 2L;

    private static final Long DEFAULT_ACADEMICYEAR_ID = 1L;
    private static final Long UPDATED_ACADEMICYEAR_ID = 2L;

    private static final Long DEFAULT_STUDENT_ID = 1L;
    private static final Long UPDATED_STUDENT_ID = 2L;

    @Autowired
    private StudentExamReportRepository studentExamReportRepository;

    @Autowired
    private StudentExamReportMapper studentExamReportMapper;

    @Autowired
    private StudentExamReportService studentExamReportService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restStudentExamReportMockMvc;

    private StudentExamReport studentExamReport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentExamReportResource studentExamReportResource = new StudentExamReportResource(studentExamReportService);
        this.restStudentExamReportMockMvc = MockMvcBuilders.standaloneSetup(studentExamReportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentExamReport createEntity(EntityManager em) {
        StudentExamReport studentExamReport = new StudentExamReport()
            .marksObtained(DEFAULT_MARKS_OBTAINED)
            .comments(DEFAULT_COMMENTS)
            .gOp(DEFAULT_G_OP)
            .sectionId(DEFAULT_SECTION_ID)
            .subjectId(DEFAULT_SUBJECT_ID)
            .departmentId(DEFAULT_DEPARTMENT_ID)
            .batchId(DEFAULT_BATCH_ID)
            .academicyearId(DEFAULT_ACADEMICYEAR_ID)
            .studentId(DEFAULT_STUDENT_ID);
        return studentExamReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentExamReport createUpdatedEntity(EntityManager em) {
        StudentExamReport studentExamReport = new StudentExamReport()
            .marksObtained(UPDATED_MARKS_OBTAINED)
            .comments(UPDATED_COMMENTS)
            .gOp(UPDATED_G_OP)
            .sectionId(UPDATED_SECTION_ID)
            .subjectId(UPDATED_SUBJECT_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .batchId(UPDATED_BATCH_ID)
            .academicyearId(UPDATED_ACADEMICYEAR_ID)
            .studentId(UPDATED_STUDENT_ID);
        return studentExamReport;
    }

    @BeforeEach
    public void initTest() {
        studentExamReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentExamReport() throws Exception {
        int databaseSizeBeforeCreate = studentExamReportRepository.findAll().size();

        // Create the StudentExamReport
        StudentExamReportDTO studentExamReportDTO = studentExamReportMapper.toDto(studentExamReport);
        restStudentExamReportMockMvc.perform(post("/api/student-exam-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentExamReportDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentExamReport in the database
        List<StudentExamReport> studentExamReportList = studentExamReportRepository.findAll();
        assertThat(studentExamReportList).hasSize(databaseSizeBeforeCreate + 1);
        StudentExamReport testStudentExamReport = studentExamReportList.get(studentExamReportList.size() - 1);
        assertThat(testStudentExamReport.getMarksObtained()).isEqualTo(DEFAULT_MARKS_OBTAINED);
        assertThat(testStudentExamReport.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testStudentExamReport.getgOp()).isEqualTo(DEFAULT_G_OP);
        assertThat(testStudentExamReport.getSectionId()).isEqualTo(DEFAULT_SECTION_ID);
        assertThat(testStudentExamReport.getSubjectId()).isEqualTo(DEFAULT_SUBJECT_ID);
        assertThat(testStudentExamReport.getDepartmentId()).isEqualTo(DEFAULT_DEPARTMENT_ID);
        assertThat(testStudentExamReport.getBatchId()).isEqualTo(DEFAULT_BATCH_ID);
        assertThat(testStudentExamReport.getAcademicyearId()).isEqualTo(DEFAULT_ACADEMICYEAR_ID);
        assertThat(testStudentExamReport.getStudentId()).isEqualTo(DEFAULT_STUDENT_ID);
    }

    @Test
    @Transactional
    public void createStudentExamReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentExamReportRepository.findAll().size();

        // Create the StudentExamReport with an existing ID
        studentExamReport.setId(1L);
        StudentExamReportDTO studentExamReportDTO = studentExamReportMapper.toDto(studentExamReport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentExamReportMockMvc.perform(post("/api/student-exam-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentExamReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentExamReport in the database
        List<StudentExamReport> studentExamReportList = studentExamReportRepository.findAll();
        assertThat(studentExamReportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStudentExamReports() throws Exception {
        // Initialize the database
        studentExamReportRepository.saveAndFlush(studentExamReport);

        // Get all the studentExamReportList
        restStudentExamReportMockMvc.perform(get("/api/student-exam-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentExamReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].marksObtained").value(hasItem(DEFAULT_MARKS_OBTAINED)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].gOp").value(hasItem(DEFAULT_G_OP)))
            .andExpect(jsonPath("$.[*].sectionId").value(hasItem(DEFAULT_SECTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].subjectId").value(hasItem(DEFAULT_SUBJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].academicyearId").value(hasItem(DEFAULT_ACADEMICYEAR_ID.intValue())))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID.intValue())));
    }

    @Test
    @Transactional
    public void getStudentExamReport() throws Exception {
        // Initialize the database
        studentExamReportRepository.saveAndFlush(studentExamReport);

        // Get the studentExamReport
        restStudentExamReportMockMvc.perform(get("/api/student-exam-reports/{id}", studentExamReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studentExamReport.getId().intValue()))
            .andExpect(jsonPath("$.marksObtained").value(DEFAULT_MARKS_OBTAINED))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.gOp").value(DEFAULT_G_OP))
            .andExpect(jsonPath("$.sectionId").value(DEFAULT_SECTION_ID.intValue()))
            .andExpect(jsonPath("$.subjectId").value(DEFAULT_SUBJECT_ID.intValue()))
            .andExpect(jsonPath("$.departmentId").value(DEFAULT_DEPARTMENT_ID.intValue()))
            .andExpect(jsonPath("$.batchId").value(DEFAULT_BATCH_ID.intValue()))
            .andExpect(jsonPath("$.academicyearId").value(DEFAULT_ACADEMICYEAR_ID.intValue()))
            .andExpect(jsonPath("$.studentId").value(DEFAULT_STUDENT_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentExamReport() throws Exception {
        // Get the studentExamReport
        restStudentExamReportMockMvc.perform(get("/api/student-exam-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentExamReport() throws Exception {
        // Initialize the database
        studentExamReportRepository.saveAndFlush(studentExamReport);

        int databaseSizeBeforeUpdate = studentExamReportRepository.findAll().size();

        // Update the studentExamReport
        StudentExamReport updatedStudentExamReport = studentExamReportRepository.findById(studentExamReport.getId()).get();
        // Disconnect from session so that the updates on updatedStudentExamReport are not directly saved in db
        em.detach(updatedStudentExamReport);
        updatedStudentExamReport
            .marksObtained(UPDATED_MARKS_OBTAINED)
            .comments(UPDATED_COMMENTS)
            .gOp(UPDATED_G_OP)
            .sectionId(UPDATED_SECTION_ID)
            .subjectId(UPDATED_SUBJECT_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .batchId(UPDATED_BATCH_ID)
            .academicyearId(UPDATED_ACADEMICYEAR_ID)
            .studentId(UPDATED_STUDENT_ID);
        StudentExamReportDTO studentExamReportDTO = studentExamReportMapper.toDto(updatedStudentExamReport);

        restStudentExamReportMockMvc.perform(put("/api/student-exam-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentExamReportDTO)))
            .andExpect(status().isOk());

        // Validate the StudentExamReport in the database
        List<StudentExamReport> studentExamReportList = studentExamReportRepository.findAll();
        assertThat(studentExamReportList).hasSize(databaseSizeBeforeUpdate);
        StudentExamReport testStudentExamReport = studentExamReportList.get(studentExamReportList.size() - 1);
        assertThat(testStudentExamReport.getMarksObtained()).isEqualTo(UPDATED_MARKS_OBTAINED);
        assertThat(testStudentExamReport.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testStudentExamReport.getgOp()).isEqualTo(UPDATED_G_OP);
        assertThat(testStudentExamReport.getSectionId()).isEqualTo(UPDATED_SECTION_ID);
        assertThat(testStudentExamReport.getSubjectId()).isEqualTo(UPDATED_SUBJECT_ID);
        assertThat(testStudentExamReport.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testStudentExamReport.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testStudentExamReport.getAcademicyearId()).isEqualTo(UPDATED_ACADEMICYEAR_ID);
        assertThat(testStudentExamReport.getStudentId()).isEqualTo(UPDATED_STUDENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentExamReport() throws Exception {
        int databaseSizeBeforeUpdate = studentExamReportRepository.findAll().size();

        // Create the StudentExamReport
        StudentExamReportDTO studentExamReportDTO = studentExamReportMapper.toDto(studentExamReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentExamReportMockMvc.perform(put("/api/student-exam-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentExamReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentExamReport in the database
        List<StudentExamReport> studentExamReportList = studentExamReportRepository.findAll();
        assertThat(studentExamReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentExamReport() throws Exception {
        // Initialize the database
        studentExamReportRepository.saveAndFlush(studentExamReport);

        int databaseSizeBeforeDelete = studentExamReportRepository.findAll().size();

        // Delete the studentExamReport
        restStudentExamReportMockMvc.perform(delete("/api/student-exam-reports/{id}", studentExamReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentExamReport> studentExamReportList = studentExamReportRepository.findAll();
        assertThat(studentExamReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
