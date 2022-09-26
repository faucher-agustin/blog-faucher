package com.astropay.blogfaucher.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    @Type(type="text")
    private String body;
    private String email;
    @Column(name = "post_id")
    private Long postId;
}
