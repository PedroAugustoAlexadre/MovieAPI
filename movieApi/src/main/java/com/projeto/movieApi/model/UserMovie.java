package com.projeto.movieApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "user_movies")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UserMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WatchStatus status;

    @Min(1) @Max(10)
    @Column(name = "user_rating")
    private Integer userRating;

    @Column(name = "user_review", columnDefinition = "TEXT")
    private String userReview;

    @Column(name = "added_at", updatable = false)
    private Instant addedAt;

    @PrePersist
    protected void onCreate() {
        this.addedAt = Instant.now();
    }
}