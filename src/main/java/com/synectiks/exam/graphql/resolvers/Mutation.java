package com.synectiks.exam.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.google.common.collect.Lists;
import com.synectiks.exam.domain.AcademicExamSetting;
import com.synectiks.exam.domain.CompetitiveExam;
import com.synectiks.exam.domain.StudentExamReport;
import com.synectiks.exam.domain.TypeOfGrading;
import com.synectiks.exam.graphql.types.AcademicExamSetting.*;
import com.synectiks.exam.graphql.types.TypeOfGrading.*;
import com.synectiks.exam.graphql.types.CompetitiveExam.*;
import com.synectiks.exam.graphql.types.StudentExamReport.*;
import com.synectiks.exam.repository.AcademicExamSettingRepository;
import com.synectiks.exam.repository.CompetitiveExamRepository;
import com.synectiks.exam.repository.StudentExamReportRepository;
import com.synectiks.exam.repository.TypeOfGradingRepository;
import com.synectiks.exam.service.util.CommonUtil;
import com.synectiks.exam.service.util.DateFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {
    private final static Logger logger = LoggerFactory.getLogger(Mutation.class);

    @Autowired
    private AcademicExamSettingRepository academicExamSettingRepository;

    @Autowired
    private TypeOfGradingRepository typeOfGradingRepository;

    @Autowired
    private StudentExamReportRepository studentExamReportRepository;

    @Autowired
    private CompetitiveExamRepository competitiveExamRepository;


    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    public AddAcademicExamSettingPayload addAcademicExamSetting(List<AddAcademicExamSettingInput> list) {
        AcademicExamSetting academicExamSetting = null;
        int countvalue = getCountvalueId()+1;
        for(AddAcademicExamSettingInput input: list ) {

            academicExamSetting = CommonUtil.createCopyProperties(input, AcademicExamSetting.class);
            if("GRADE".equalsIgnoreCase(input.getGradeType().toString())) {
                Optional<TypeOfGrading> otg = typeOfGradingRepository.findById((input.getTypeOfGradingId()));
                if(otg.isPresent()) {
                    academicExamSetting.setTypeOfGrading(otg.get());
                }
            }

            LocalDate exmDt =  DateFormatUtil.convertLocalDateFromUtilDate(input.getExamDate());
            academicExamSetting.setExamDate(exmDt);
            academicExamSetting.setCountvalue(new Long(countvalue));
            academicExamSetting.setBranchId(input.getBranchId());
            academicExamSetting.setSubjectId(input.getSubjectId());
            academicExamSetting.setBatchId(input.getBatchId());
            academicExamSetting.setAcademicyearId(input.getAcademicyearId());
            academicExamSetting.setSectionId(input.getSectionId());
            academicExamSetting.setDepartmentId(input.getDepartmentId());

            academicExamSetting.setExamDate(DateFormatUtil.convertLocalDateFromUtilDate(input.getExamDate()));
            this.academicExamSettingRepository.save(academicExamSetting);

        }
        return  new AddAcademicExamSettingPayload(academicExamSetting);
    }
    private int getCountvalueId(){
        String sql = "select max(countvalue) from academic_exam_setting";
        Query query = this.entityManager.createNativeQuery(sql);
        Object countvalue = query.getSingleResult();
        if(countvalue == null) {
            return 0;
        }
        return ((BigInteger)countvalue).intValue() ;
    }

    public UpdateAcademicExamSettingPayload updateAcademicExamSetting(UpdateAcademicExamSettingInput updateAcademicExamSettingInput) {
        AcademicExamSetting academicExamSetting = academicExamSettingRepository.findById(updateAcademicExamSettingInput.getId()).get();
        if("GRADE".equalsIgnoreCase(updateAcademicExamSettingInput.getGradeType().toString())) {
            Optional<TypeOfGrading> otg = typeOfGradingRepository.findById((updateAcademicExamSettingInput.getTypeOfGradingId()));
            if(otg.isPresent()) {
                academicExamSetting.setTypeOfGrading(otg.get());
            }
        }

        if (updateAcademicExamSettingInput.getExamName() != null) {
            academicExamSetting.setExamName(updateAcademicExamSettingInput.getExamName());
        }
        if (updateAcademicExamSettingInput.getSemester() != null) {
            academicExamSetting.setSemester(updateAcademicExamSettingInput.getSemester());
        }
        if (updateAcademicExamSettingInput.getExamDate() != null) {
            academicExamSetting.setExamDate(DateFormatUtil.convertLocalDateFromUtilDate(updateAcademicExamSettingInput.getExamDate()));
        }
        if (updateAcademicExamSettingInput.getStartTime() != null) {
            academicExamSetting.setStartTime(updateAcademicExamSettingInput.getStartTime());
        }
        if (updateAcademicExamSettingInput.getEndTime() != null) {
            academicExamSetting.setEndTime(updateAcademicExamSettingInput.getEndTime());
        }
        if (updateAcademicExamSettingInput.getGradeType() != null) {
            academicExamSetting.setGradeType(updateAcademicExamSettingInput.getGradeType());
        }
        if (updateAcademicExamSettingInput.getTotal() != null) {
            academicExamSetting.setTotal(updateAcademicExamSettingInput.getTotal());
        }
        if (updateAcademicExamSettingInput.getPassing() != null) {
            academicExamSetting.setPassing(updateAcademicExamSettingInput.getPassing());
        }
        if (updateAcademicExamSettingInput.getActions() != null) {
            academicExamSetting.setActions(updateAcademicExamSettingInput.getActions());
        }
        if (updateAcademicExamSettingInput.getDepartmentId() != null) {
            academicExamSetting.setDepartmentId(updateAcademicExamSettingInput.getDepartmentId());
        }
        if (updateAcademicExamSettingInput.getAcademicyearId() != null) {
            academicExamSetting.setAcademicyearId(updateAcademicExamSettingInput.getAcademicyearId());
        }
        if (updateAcademicExamSettingInput.getSubjectId() != null) {
            academicExamSetting.setSubjectId(updateAcademicExamSettingInput.getSubjectId());
        }
        if (updateAcademicExamSettingInput.getSectionId() != null) {
            academicExamSetting.setSectionId(updateAcademicExamSettingInput.getSectionId());
        }
        if (updateAcademicExamSettingInput.getBatchId() != null) {
            academicExamSetting.setBatchId(updateAcademicExamSettingInput.getBatchId());
        }
        if (updateAcademicExamSettingInput.getBranchId() != null) {
            academicExamSetting.setBranchId(updateAcademicExamSettingInput.getBranchId());
        }
        if (updateAcademicExamSettingInput.getTypeOfGradingId() != null) {
            final TypeOfGrading typeOfGrading = typeOfGradingRepository.findById(updateAcademicExamSettingInput.getTypeOfGradingId()).get();
            academicExamSetting.setTypeOfGrading(typeOfGrading);
        }

//        if (updateAcademicExamSettingInput.getCountvalue() != null) {
//            academicExamSetting.setCountvalue(updateAcademicExamSettingInput.getCountvalue());
//        }

        academicExamSettingRepository.save(academicExamSetting);

        return new UpdateAcademicExamSettingPayload(academicExamSetting);
    }

    public RemoveAcademicExamSettingPayload removeAcademicExamSetting(RemoveAcademicExamSettingInput removeAcademicExamSettingsInput) {
        AcademicExamSetting academicExamSetting = academicExamSettingRepository.findById(removeAcademicExamSettingsInput.getAcademicExamSettingId()).get();
        academicExamSettingRepository.delete(academicExamSetting);
        return new RemoveAcademicExamSettingPayload(Lists.newArrayList(academicExamSettingRepository.findAll()));
    }
    public AddTypeOfGradingPayload addTypeOfGrading(List<AddTypeOfGradingInput> list) {
        TypeOfGrading typeOfGrading = null;
        // get the max id from database.
        int groupvalue = getNextGradeId()+1;
        for (AddTypeOfGradingInput input : list) {
            typeOfGrading = CommonUtil.createCopyProperties(input, TypeOfGrading.class);
            typeOfGrading.setGroupvalue(new Long(groupvalue));
            this.typeOfGradingRepository.save(typeOfGrading);
        }
        return new AddTypeOfGradingPayload(typeOfGrading);
    }

    private int getNextGradeId(){
        String sql = "select max(groupvalue) from type_of_grading";
        Query query = this.entityManager.createNativeQuery(sql);
        Object groupValue = query.getSingleResult();
        if(groupValue == null) {
            return 0;
        }
        return ((BigInteger)groupValue).intValue() ;
    }

    public UpdateTypeOfGradingPayload updateTypeOfGrading(UpdateTypeOfGradingInput updateTypeOfGradingInput) {
        TypeOfGrading typeOfGrading = typeOfGradingRepository.findById(updateTypeOfGradingInput.getId()).get();
        if (updateTypeOfGradingInput.getMinMarks() != null) {
            typeOfGrading.setMinMarks(updateTypeOfGradingInput.getMinMarks());
        }
        if (updateTypeOfGradingInput.getMaxMarks() != null) {
            typeOfGrading.setMaxMarks(updateTypeOfGradingInput.getMaxMarks());
        }
        if (updateTypeOfGradingInput.getGrades() != null) {
            typeOfGrading.setGrades(updateTypeOfGradingInput.getGrades());
        }
        typeOfGradingRepository.save(typeOfGrading);

        return new UpdateTypeOfGradingPayload(typeOfGrading);

    }

    public RemoveTypeOfGradingPayload removeTypeOfGrading(RemoveTypeOfGradingInput removeTypeOfGradingInput) {
        TypeOfGrading typeOfGrading = typeOfGradingRepository.findById(removeTypeOfGradingInput.getTypeOfGradingId()).get();
        typeOfGradingRepository.delete(typeOfGrading);
        return new RemoveTypeOfGradingPayload(Lists.newArrayList(typeOfGradingRepository.findAll()));
    }

    public AddCompetitiveExamPayload addCompetitiveExam(AddCompetitiveExamInput addCompetitiveExamInput) {
//        final Student student = studentRepository.findById(addCompetitiveExamInput.getStudentId()).get();

        CompetitiveExam competitiveExam = new CompetitiveExam();
        competitiveExam.setStudentId(addCompetitiveExamInput.getStudentId());
        competitiveExam.setTestName(addCompetitiveExamInput.getTestName());
        competitiveExam.setTestScore(addCompetitiveExamInput.getTestScore());
        competitiveExam.setEnrollmentNo(addCompetitiveExamInput.getEnrollmentNo());
        competitiveExam.setRank(addCompetitiveExamInput.getRank());
        this.competitiveExamRepository.save(competitiveExam);
        return new AddCompetitiveExamPayload(competitiveExam);
    }

    public UpdateCompetitiveExamPayload updateCompetitiveExam(UpdateCompetitiveExamInput updateCompetitiveExamInput) {
        final CompetitiveExam competitiveExam = competitiveExamRepository.findById(updateCompetitiveExamInput.getId()).get();

        if (updateCompetitiveExamInput.getTestName() != null){
            competitiveExam.setTestName(updateCompetitiveExamInput.getTestName());
        }
        if(updateCompetitiveExamInput.getTestScore() != null){
            competitiveExam.setTestScore(updateCompetitiveExamInput.getTestScore());
        }
        if(updateCompetitiveExamInput.getEnrollmentNo() != null){
            competitiveExam.setEnrollmentNo(updateCompetitiveExamInput.getEnrollmentNo());
        }

        if(updateCompetitiveExamInput.getRank() != null){
            competitiveExam.setRank(updateCompetitiveExamInput.getRank());
        }

        if (updateCompetitiveExamInput.getStudentId() != null) {
//            final Student student = studentRepository.findById(updateCompetitiveExamInput.getStudentId()).get();
            competitiveExam.setStudentId(updateCompetitiveExamInput.getStudentId());
        }
        competitiveExamRepository.save(competitiveExam);
        return new UpdateCompetitiveExamPayload(competitiveExam);
    }


    public RemoveCompetitiveExamPayload removeCompetitiveExam(RemoveCompetitiveExamInput removeCompetitiveExamInput) {
        CompetitiveExam CompetitiveExam = competitiveExamRepository.findById(removeCompetitiveExamInput.getCompetitiveExamId()).get();
        competitiveExamRepository.delete(CompetitiveExam);
        return new RemoveCompetitiveExamPayload(Lists.newArrayList(competitiveExamRepository.findAll()));
    }
    public AddStudentExamReportPayload addStudentExamReport(AddStudentExamReportInput addStudentExamReportInput) {
        final StudentExamReport studentExamReport = new StudentExamReport();

        TypeOfGrading typeOfGrading = typeOfGradingRepository.findById(addStudentExamReportInput.getTypeOfGradingId()).get();
        AcademicExamSetting academicExamSetting = academicExamSettingRepository.findById(addStudentExamReportInput.getAcademicExamSettingId()).get();
//        AcademicYear academicYear = academicYearRepository.findById(addStudentExamReportInput.getAcademicyearId()).get();
//        Student student = studentRepository.findById(addStudentExamReportInput.getStudentId()).get();
//        Batch batch = batchRepository.findById(addStudentExamReportInput.getBatchId()).get();
//        Department department = departmentRepository.findById(addStudentExamReportInput.getDepartmentId()).get();
//        Subject subject = subjectRepository.findById(addStudentExamReportInput.getSubjectId()).get();
//        Section section =sectionRepository.findById(addStudentExamReportInput.getSectionId()).get();
        studentExamReport.setTypeOfGrading(typeOfGrading);
        studentExamReport.setAcademicyearId(addStudentExamReportInput.getAcademicyearId());
        studentExamReport.setAcademicExamSetting(academicExamSetting);
        studentExamReport.setBatchId(addStudentExamReportInput.getBatchId());
        studentExamReport.setStudentId(addStudentExamReportInput.getStudentId());
        studentExamReport.setDepartmentId(addStudentExamReportInput.getDepartmentId());
        studentExamReport.setSectionId(addStudentExamReportInput.getSectionId());
        studentExamReport.setSubjectId(addStudentExamReportInput.getSubjectId());
        studentExamReport.setMarksObtained(addStudentExamReportInput.getMarksObtained());
        studentExamReport.setComments(addStudentExamReportInput.getComments());
        studentExamReport.setgOp(addStudentExamReportInput.getgOp());
        studentExamReportRepository.save(studentExamReport);
        return new AddStudentExamReportPayload(studentExamReport);
    }

    public UpdateStudentExamReportPayload updateStudentExamReport(UpdateStudentExamReportInput updateStudentExamReportInput) {
        StudentExamReport studentExamReport = studentExamReportRepository.findById(updateStudentExamReportInput.getId()).get();


        if (updateStudentExamReportInput.getMarksObtained() != null) {
            studentExamReport.setMarksObtained(updateStudentExamReportInput.getMarksObtained());
        }
        if (updateStudentExamReportInput.getComments() != null) {
            studentExamReport.setComments(updateStudentExamReportInput.getComments());
        }
        if (updateStudentExamReportInput.getgOp() != null) {
            studentExamReport.setgOp(updateStudentExamReportInput.getgOp());
        }
        if (updateStudentExamReportInput.getAcademicExamSettingId() != null) {
            final AcademicExamSetting academicExamSetting = academicExamSettingRepository.findById(updateStudentExamReportInput.getAcademicExamSettingId()).get();
            studentExamReport.setAcademicExamSetting(academicExamSetting);
        }
        if (updateStudentExamReportInput.getAcademicyearId() != null) {
//            final AcademicYear academicYear = academicYearRepository.findById(updateStudentExamReportInput.getAcademicyearId()).get();
            studentExamReport.setAcademicyearId(updateStudentExamReportInput.getAcademicyearId());
        }
        if (updateStudentExamReportInput.getStudentId() != null) {
//            final Student student = studentRepository.findById(updateStudentExamReportInput.getStudentId()).get();
            studentExamReport.setStudentId(updateStudentExamReportInput.getStudentId());
        }
        if (updateStudentExamReportInput.getBatchId() != null) {
//            final Batch batch = batchRepository.findById(updateStudentExamReportInput.getBatchId()).get();
            studentExamReport.setBatchId(updateStudentExamReportInput.getBatchId());
        }
        if (updateStudentExamReportInput.getDepartmentId() != null) {
//            final Department department = departmentRepository.findById(updateStudentExamReportInput.getDepartmentId()).get();
            studentExamReport.setDepartmentId(updateStudentExamReportInput.getDepartmentId());
        }
        if (updateStudentExamReportInput.getTypeOfGradingId() != null) {
            final TypeOfGrading typeOfGrading = typeOfGradingRepository.findById(updateStudentExamReportInput.getTypeOfGradingId()).get();
            studentExamReport.setTypeOfGrading(typeOfGrading);
        }
        if (updateStudentExamReportInput.getSectionId() != null) {
//            final Section section = sectionRepository.findById(updateStudentExamReportInput.getSectionId()).get();
            studentExamReport.setSectionId(updateStudentExamReportInput.getSectionId());
        }
        if (updateStudentExamReportInput.getSubjectId() != null) {
//            final Subject subject = subjectRepository.findById(updateStudentExamReportInput.getSubjectId()).get();
            studentExamReport.setSubjectId(updateStudentExamReportInput.getSubjectId());
        }
        studentExamReportRepository.save(studentExamReport);

        return new UpdateStudentExamReportPayload(studentExamReport);
    }

    public RemoveStudentExamReportPayload removeStudentExamReport(RemoveStudentExamReportInput removeStudentExamReportsInput) {
        StudentExamReport studentExamReport = studentExamReportRepository.findById(removeStudentExamReportsInput.getStudentExamReportId()).get();
        studentExamReportRepository.delete(studentExamReport);
        return new RemoveStudentExamReportPayload(Lists.newArrayList(studentExamReportRepository.findAll()));
    }


}
