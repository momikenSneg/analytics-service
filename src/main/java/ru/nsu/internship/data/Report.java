package ru.nsu.internship.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private String templateId;
    private Map<String, String> variables;
}
