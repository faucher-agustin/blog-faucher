package com.astropay.blogfaucher.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    @Type(type="text")
    private String body;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
