package com.somethingsblog.app.ws.io.repository;

import com.somethingsblog.app.ws.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {
    UserEntity findUserEntityByEmail(String email);
    UserEntity findUserEntityByUserId(String userId);
}