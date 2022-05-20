package com.projects.service;

import com.projects.exception.NotFoundException;
import com.projects.models.Publisher;
import com.projects.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.*;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;

    @Transactional(readOnly = true, propagation = SUPPORTS)
    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = SUPPORTS)
    public Publisher findPublisherById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Publisher with id %d not found", id)));
    }

    public void createPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public void updatePublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public void deletePublisherById(Long id) {
        boolean exists = publisherRepository.existsById(id);
        if(!exists) {
            throw new NotFoundException(String.format("Publisher with id %d not found",id));
        }
        publisherRepository.deleteById(id);
    }

}
