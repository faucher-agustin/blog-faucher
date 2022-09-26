package com.astropay.blogfaucher.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    @Type(type="text")
    private String body;

    @Column(name = "user_id")
    private Long userId;

    public Post(Long id, String title, String body, Long userId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
    }
}
