package ru.nsu.internship.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "template")
public class Template {
    @Id
    @Column(name = "template_id", nullable = false, updatable = false)
    private String templateId;

    @Column(name = "template", nullable = false, length = 512)
    private String template;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
    private List<Recipient> recipients = new ArrayList<>();

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
    private List<VarType> varTypes = new ArrayList<>();

    public void addRecipient(Recipient recipient){
        recipients.add(recipient);
    }

    public void addVarType(VarType varType){
        varTypes.add(varType);
    }

    public Template(String templateId, String template){
        this.template = template;
        this.templateId = templateId;
    }
}
