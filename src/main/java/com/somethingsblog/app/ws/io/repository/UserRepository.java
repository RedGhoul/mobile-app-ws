package com.somethingsblog.app.ws.io.repository;

import com.somethingsblog.app.ws.io.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {

    @EntityGraph(attributePaths = {"addresses"})
    @Query(value="select u from users u where u.email = ?1")
    UserEntity findUserEntityByEmailWithAddresses(String email);
    UserEntity findUserEntityByEmail(String email);
    //@EntityGraph(attributePaths = {"addresses"})
    UserEntity findUserEntityByUserId(String userId);
}
