package com.synectiks.exam.graphql.types.AcademicExamSetting;

import com.synectiks.exam.domain.vo.CmsAcademicExamSettingVo;

public class AddAcademicExamSettingPayload extends AbstractAcademicExamSettingPayload{
    public AddAcademicExamSettingPayload(CmsAcademicExamSettingVo cmsAcademicExamSettingVo) {
        super(cmsAcademicExamSettingVo);
    }
}
