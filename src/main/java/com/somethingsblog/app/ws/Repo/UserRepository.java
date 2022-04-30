package com.somethingsblog.app.ws.Repo;

import com.somethingsblog.app.ws.io.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findUserEntityByEmail(String email);
}
