package com.synectiks.exam.graphql.types.AcademicExamSetting;

import com.synectiks.exam.domain.AcademicExamSetting;
import com.synectiks.exam.domain.vo.CmsAcademicExamSettingVo;

public class UpdateAcademicExamSettingPayload extends  AddAcademicExamSettingPayload{
    public  UpdateAcademicExamSettingPayload(CmsAcademicExamSettingVo cmsAcademicExamSettingVo)
    {
        super(cmsAcademicExamSettingVo);
    }
}
