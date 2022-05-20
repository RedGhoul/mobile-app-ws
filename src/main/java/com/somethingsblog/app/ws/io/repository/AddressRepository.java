package com.somethingsblog.app.ws.io.repository;

import com.somethingsblog.app.ws.io.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<UserEntity,Long> {
}
