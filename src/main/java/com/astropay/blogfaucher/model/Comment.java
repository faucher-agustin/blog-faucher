package com.astropay.blogfaucher.model;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    @Type(type="text")
    private String body;
    private String email;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
