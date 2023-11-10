package com.compassuol.msnotification.repository;

import com.compassuol.msnotification.model.NotifyModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotifyRepository extends MongoRepository<NotifyModel, String> {
}
