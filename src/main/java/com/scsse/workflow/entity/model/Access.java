package com.scsse.workflow.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name ="access")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Access implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String url;

    @Column
    private String title;

    @Override
    public String toString() {
        return "Access{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
