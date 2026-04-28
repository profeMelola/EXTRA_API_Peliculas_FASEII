package es.daw.extra_api_peliculas.service;

import es.daw.extra_api_peliculas.dto.response.ActorCastDto;
import es.daw.extra_api_peliculas.dto.response.MovieResponseDto;
import es.daw.extra_api_peliculas.dto.response.MovieWithCastDto;
import es.daw.extra_api_peliculas.entity.Movie;
import es.daw.extra_api_peliculas.enums.Genre;
import es.daw.extra_api_peliculas.exception.ResourceNotFoundException;
import es.daw.extra_api_peliculas.mapper.MovieMapper;
import es.daw.extra_api_peliculas.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public List<MovieWithCastDto> getAllMoviesWithCast() {
        //return movieRepository.findAll()
        return movieRepository.findAllWithCast()
                .stream()
                .map(movieMapper::toMovieWithCastDto)
                //.collect(Collectors.toList());
                .toList();


    }

    public List<MovieResponseDto> getMovies(Genre genre) {
        List<Movie> movies = (genre == null) ? movieRepository.findAll() : movieRepository.findByGenre(genre);
        return movies.stream()
                .map(movieMapper::toResponseDto)
                //.collect(Collectors.toList());
                .toList();
    }

    public MovieResponseDto getMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
        return movieMapper.toResponseDto(movie);
    }

    public List<ActorCastDto> getMovieCast(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
        return movieMapper.toActorCastDtos(movie);
    }

}
