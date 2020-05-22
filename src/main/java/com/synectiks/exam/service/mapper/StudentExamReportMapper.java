package com.synectiks.exam.service.mapper;


import com.synectiks.exam.domain.*;
import com.synectiks.exam.service.dto.StudentExamReportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StudentExamReport} and its DTO {@link StudentExamReportDTO}.
 */
@Mapper(componentModel = "spring", uses = {AcademicExamSettingMapper.class, TypeOfGradingMapper.class})
public interface StudentExamReportMapper extends EntityMapper<StudentExamReportDTO, StudentExamReport> {

    @Mapping(source = "academicExamSetting.id", target = "academicExamSettingId")
    @Mapping(source = "typeOfGrading.id", target = "typeOfGradingId")
    StudentExamReportDTO toDto(StudentExamReport studentExamReport);

    @Mapping(source = "academicExamSettingId", target = "academicExamSetting")
    @Mapping(source = "typeOfGradingId", target = "typeOfGrading")
    StudentExamReport toEntity(StudentExamReportDTO studentExamReportDTO);

    default StudentExamReport fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentExamReport studentExamReport = new StudentExamReport();
        studentExamReport.setId(id);
        return studentExamReport;
    }
}
