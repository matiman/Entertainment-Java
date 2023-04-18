package com.bill.entertainment.service;

import com.bill.entertainment.entity.Media;
import com.bill.entertainment.exception.CreativesNotFoundException;
import com.bill.entertainment.exception.MediaDeletionException;
import com.bill.entertainment.exception.MediaNotFoundException;
import com.bill.entertainment.exception.MediaValidationException;

import java.util.List;

public interface MediaService<T extends Media> {
    List<T> getAll();
    T getById(Long id) throws MediaNotFoundException;
    T create(T media) throws MediaValidationException, CreativesNotFoundException;
    T update(Long id, T media) throws MediaNotFoundException, MediaValidationException, CreativesNotFoundException;
    void delete(Long id) throws MediaNotFoundException, MediaDeletionException;

    void validateMedia(Media media,boolean isNewMedia) throws MediaValidationException, CreativesNotFoundException;

}

