package ru.nsu.internship.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipient")
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, updatable = false)
    private long id;

    @Column(name="url", nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name="template_id", nullable=false)
    private Template template;

    public Recipient(String url, Template template){
        this.url = url;
        this.template = template;
    }
}
