package com.example.hibernatejpaspringdata.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.time.LocalDateTime;

@MappedSuperclass
public class AuditableEntity {
    @Version
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedDate = LocalDateTime.now();
    }


    //<editor-fold desc="Description">
    public Integer getVersion() {
        return version;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }
    //</editor-fold>
}
