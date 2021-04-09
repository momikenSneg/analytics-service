package ru.nsu.internship.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.internship.models.Template;

@Repository
public interface TemplateRepository extends CrudRepository<Template, String> {

}
