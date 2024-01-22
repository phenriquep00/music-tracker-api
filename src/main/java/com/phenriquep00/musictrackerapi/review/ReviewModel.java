package com.phenriquep00.musictrackerapi.review;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.phenriquep00.musictrackerapi.user.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "tb_review")
public class ReviewModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @Column(columnDefinition = "TEXT")
    private String text;
    
    private int rating;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}
