package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    List<T> findAll();
    Optional<T> findById(Long id);
    void update(T t);
    Long insert(T t);
    void delete(Long id);
}
