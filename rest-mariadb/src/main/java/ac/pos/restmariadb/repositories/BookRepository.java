package ac.pos.restmariadb.repositories;

import ac.pos.restmariadb.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.name LIKE %:name%")
    List<Book> findByName(@Param("name") String name);

    @Query("SELECT b FROM Book b WHERE b.genre LIKE %:genre%")
    List<Book> findByGenre(@Param("genre") String genre);

    @Query("SELECT b FROM Book b WHERE b.releaseYear LIKE %:year%")
    List<Book> findByReleaseYear(@Param("year") Short year);
}
