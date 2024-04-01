package org.example.reactiveprograming.mapper.modelmapper;

import org.example.reactiveprograming.model.BaseModel;

public interface ModelMapper<M extends BaseModel, D> {
    M toModel(D dto);

    D toDTO(M model);
}
