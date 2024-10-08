package com.teguhs.eventra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    private @Id long id;    // snowflake id

    @NotNull @Size(min = 3, max = 128)
    @Column(nullable = false, length = 128)
    private String name;

    @NotNull() @Email @Size(min = 3, max = 128)
    @Column(nullable = false, length = 128, unique = true)
    private String email;

    @NotNull(message = "Password must not be null")
    @Size(min = 8, max = 128)
    @Column(nullable = false, length = 128)
    private String password;

    @Size(min = 8, max = 20)
    @Column(length = 20)
    private String phoneNumber;

    @Past
    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(length = 10)
    private String gender;

    @Column(name = "token_expired_at")
    private LocalDateTime tokenExpiredAt;

    // Auto-managed timestamps
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
