package ac.pos.restmariadb.repositories;

import ac.pos.restmariadb.models.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE a.name LIKE %:name%")
    List<Author> findByName(@Param("name") String name);
}
