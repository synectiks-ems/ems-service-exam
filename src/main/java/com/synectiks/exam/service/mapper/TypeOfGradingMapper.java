package com.synectiks.exam.service.mapper;


import com.synectiks.exam.domain.*;
import com.synectiks.exam.service.dto.TypeOfGradingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeOfGrading} and its DTO {@link TypeOfGradingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeOfGradingMapper extends EntityMapper<TypeOfGradingDTO, TypeOfGrading> {



    default TypeOfGrading fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeOfGrading typeOfGrading = new TypeOfGrading();
        typeOfGrading.setId(id);
        return typeOfGrading;
    }
}
