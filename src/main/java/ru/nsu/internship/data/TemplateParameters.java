package ru.nsu.internship.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateParameters {
    private String templateId;
    private String template;
    private List<String> recipients;
    public Map<String, String> varTypes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateParameters that = (TemplateParameters) o;
        return Objects.equals(templateId, that.templateId) &&
                Objects.equals(template, that.template) &&
                Objects.equals(recipients, that.recipients) &&
                Objects.equals(varTypes, that.varTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templateId, template, recipients, varTypes);
    }
}
