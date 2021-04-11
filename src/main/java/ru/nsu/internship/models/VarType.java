package ru.nsu.internship.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "var_type")
public class VarType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, updatable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name="template_id", nullable=false)
    private Template template;

    public VarType(String name, String type, Template template){
        this.name = name;
        this.type = type;
        this.template = template;
    }

    public VarType(String name, String type){
        this.name = name;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VarType type1 = (VarType) o;
        return Objects.equals(name, type1.name) &&
                Objects.equals(type, type1.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
