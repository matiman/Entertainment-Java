package com.bill.entertainment.service;

import java.util.List;

import com.bill.entertainment.entity.Creatives;
import com.bill.entertainment.exception.*;

public interface CreativesService<T extends Creatives> {
    List<T> getAll();
    T getById(Long id) throws CreativesNotFoundException;
    T create(T creatives) throws CreativesValidationException;
    T update(Long id, T creatives) throws CreativesNotFoundException, CreativesValidationException;
    void delete(Long id) throws Exception;
}

