package com.bill.entertainment.service;

import com.bill.entertainment.entity.Creatives;
import com.bill.entertainment.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class CreativesServiceImpl<T extends Creatives, R extends JpaRepository<T, Long>> implements CreativesService<T> {
    @Autowired
    protected R repository;

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public T getById(Long id) throws CreativesNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new CreativesNotFoundException("Creatives not found with id: " + id));
    }

    @Override
    public T create(T creatives) throws CreativesValidationException {
        validateCreatives(creatives);
        return repository.save(creatives);
    }

    @Override
    public T update(Long id, T creatives) throws CreativesNotFoundException, CreativesValidationException {
        T existingCreatives = getById(id);

        validateCreatives(existingCreatives);
        return repository.save(existingCreatives);
    }

    @Override
    public void delete(Long id) throws Exception {
        T existingCreatives = getById(id);
        try {
            repository.delete(existingCreatives);
        } catch (Exception e) {
            throw new CreativesDeletionException("Failed to delete creatives with id: " + id);
        }
    }

    private void validateCreatives(T creatives) throws CreativesValidationException {

    }
}
