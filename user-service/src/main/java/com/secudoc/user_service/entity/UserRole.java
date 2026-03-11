package com.secudoc.user_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="user_roles")
@Data
public class UserRole {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private Long roleId;
}
