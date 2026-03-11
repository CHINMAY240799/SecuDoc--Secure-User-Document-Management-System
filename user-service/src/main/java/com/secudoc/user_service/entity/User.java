package com.secudoc.user_service.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    private String id;

    private String username;

    private String email;

    private Instant createdAt;
}