package com.somethingsblog.app.ws.io.repository;

import com.somethingsblog.app.ws.io.entity.AddressEntity;
import com.somethingsblog.app.ws.io.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<AddressEntity,Long> {
    List<AddressEntity> findAddressEntitiesByUserDetails(UserEntity userEntity);
    AddressEntity findAddressEntityByUserDetailsUserIdAndAddressId(String userId, String addressId);

}
