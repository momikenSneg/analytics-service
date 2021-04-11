package ru.nsu.internship.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template1 = (Template) o;
        template1.getRecipients().sort(Comparator.comparing(Recipient::getUrl));
        template1.getVarTypes().sort(Comparator.comparing(VarType::getName));
        return Objects.equals(templateId, template1.templateId) &&
                Objects.equals(template, template1.template) &&
                Objects.equals(recipients, template1.recipients) &&
                Objects.equals(varTypes, template1.varTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templateId, template, recipients, varTypes);
    }
}
