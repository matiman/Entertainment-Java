package com.bill.entertainment.service;

import com.bill.entertainment.entity.Media;
import com.bill.entertainment.exception.MediaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class MediaServiceImpl<T extends Media, R extends JpaRepository<T, Long>> implements MediaService<T> {
    @Autowired
    protected R mediaRepository;

    @Override
    public List<T> getAll() {
        return mediaRepository.findAll();
    }

    @Override
    public T getById(Long id) throws MediaNotFoundException {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException("Media id not found: " + id));
    }


}

