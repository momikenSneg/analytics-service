package ru.nsu.internship.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateParameters {
    private String templateId;
    private String template;
    private List<String> recipients;
    public Map<String, String> varTypes;
}
