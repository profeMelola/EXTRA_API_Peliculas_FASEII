package es.daw.extra_api_peliculas.repository;

import es.daw.extra_api_peliculas.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("""
    SELECT DISTINCT m FROM Movie m 
    JOIN FETCH m.movieCast mc
    JOIN FETCH mc.actor
        """)
    List<Movie> findAllWithCast();
}

