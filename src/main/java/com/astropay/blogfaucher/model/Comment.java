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
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(5);
        map.put("id", this.id);
        map.put("name", this.name);
        map.put("body", this.body);
        map.put("email", this.email);
        map.put("postId", this.postId);
        return map;
    }
}
