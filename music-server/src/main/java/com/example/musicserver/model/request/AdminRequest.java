package com.example.musicserver.model.request;

import lombok.Data;

@Data
public class AdminRequest {
    private Integer id;

    private String username;

    private String password;
}
