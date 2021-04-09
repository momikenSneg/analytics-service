package ru.nsu.internship.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.internship.models.Template;
import ru.nsu.internship.models.VarType;

public interface VarTypeRepository extends CrudRepository<VarType, Long> {
    void deleteAllByTemplate(Template template);
}
