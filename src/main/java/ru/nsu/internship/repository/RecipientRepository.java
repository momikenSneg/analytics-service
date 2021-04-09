package ru.nsu.internship.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.nsu.internship.models.Recipient;
import ru.nsu.internship.models.Template;

import java.util.List;

public interface RecipientRepository extends CrudRepository<Recipient, String> {
    //@Query(value = "SELECT * FROM RECIPIENT where TEMPLATE_ID=?1 and URL=?2 FETCH FIRST 1 ROWS ONLY;", nativeQuery = true)
    //Recipient getFirstByTemplateAndUrl(Template templateId, String url);
    boolean existsByTemplateAndUrl(Template templateId, String url);
    void deleteAllByTemplate(Template template);
}
