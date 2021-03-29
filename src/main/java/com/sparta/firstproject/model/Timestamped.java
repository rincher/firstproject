package com.sparta.firstproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;
//    private LocalDateTime modifiedAt = LocalDateTime.parse("2021-03-23T20:08:23.648", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}
