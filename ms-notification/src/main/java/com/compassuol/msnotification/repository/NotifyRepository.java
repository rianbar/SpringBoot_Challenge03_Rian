package com.compassuol.msnotification.repository;

import com.compassuol.msnotification.model.NotifyModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NotifyRepository extends MongoRepository<NotifyModel, String> {

    @Query("{}")
    List<NotifyModel> findAllNotifyModels();
    public List<NotifyModel> findByEvent(String event);
    public List<NotifyModel> findByEmail(String email);
}
