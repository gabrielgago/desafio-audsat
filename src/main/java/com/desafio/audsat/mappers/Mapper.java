package com.desafio.audsat.mappers;

public interface Mapper<T, D> {

    public T toEntity(D dto);
    public D fromEntity(T entity);
}
