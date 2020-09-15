package com.synectiks.exam.graphql.types.AcademicExamSetting;

import com.synectiks.exam.domain.AcademicExamSetting;
import com.synectiks.exam.domain.vo.CmsAcademicExamSettingVo;

public class AbstractAcademicExamSettingPayload {
    private final CmsAcademicExamSettingVo cmsAcademicExamSettingVo;

    public AbstractAcademicExamSettingPayload(CmsAcademicExamSettingVo cmsAcademicExamSettingVo) {
        this.cmsAcademicExamSettingVo = cmsAcademicExamSettingVo;
    }

    public CmsAcademicExamSettingVo getCmsAcademicExamSettingVo() {
        return cmsAcademicExamSettingVo;
    }
}
