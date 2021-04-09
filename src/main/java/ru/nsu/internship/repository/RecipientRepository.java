package ru.nsu.internship.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.nsu.internship.models.Recipient;
import ru.nsu.internship.models.Template;

public interface RecipientRepository extends CrudRepository<Recipient, String> {
    @Query(value = "SELECT * FROM RECIPIENT where TEMPLATE_ID=?1 and URL=?2 FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Recipient getFirstByTemplateIdAndUrl(String templateId, String url);
    @Modifying
    @Query(value = "INSERT INTO RECIPIENT(url, template_id) VALUES (?2, ?1)", nativeQuery = true)
    void insertByTemplateIdAndUrl(String templateId, String url);
    @Modifying
    @Query(value = "DELETE FROM RECIPIENT WHERE template_id=?1 AND url=?2", nativeQuery = true)
    void deleteByTemplateIdAndUrl(String templateId, String url);
    void deleteAllByTemplate(Template template);
}
