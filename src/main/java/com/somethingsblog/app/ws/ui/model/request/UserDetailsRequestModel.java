package com.somethingsblog.app.ws.ui.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDetailsRequestModel {
    private String firstName="";
    private String lastName="";
    private String email="";
    private String password="";
    private List<AddressRequestModel> addresses;
}
