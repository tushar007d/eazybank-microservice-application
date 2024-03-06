package com.tushar.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass //this annotation is used to declared this class as a base class for other classes.
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    /*
    we set updatable = false, cause we don't want to update this dields when we update the entity
    @CreatedDate is used to set the created date of the entity.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /*
    we set updatable = false, because we don't want to update this fields when we update the entity
    @CreatedBy is used to set the created by XX the entity.
     */
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    /*
    we set insertable = false, because we don't want to insert this fields when we insert the entity.
    @LastModifiedDate is used to set the last modified date of the entity.
     */
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    /*
    we set insertable = false, because we don't want to insert this fields when we insert the entity.
    @LastModifiedBy is used to set the last modified by XX the entity.
     */
    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;
}
