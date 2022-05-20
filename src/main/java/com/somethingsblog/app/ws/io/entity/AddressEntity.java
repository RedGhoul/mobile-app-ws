package com.somethingsblog.app.ws.io.entity;

import com.somethingsblog.app.ws.shard.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 30, nullable = false)
    private String addressId = "";
    @Column(length = 20, nullable = false)
    private String city;
    @Column(length = 20, nullable = false)
    private String country;
    @Column(length = 100, nullable = false)
    private String streetName;
    @Column(length = 7, nullable = false)
    private String postalCode;
    @Column(length = 10, nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name="users_id")
    private UserEntity userDetails;
}
