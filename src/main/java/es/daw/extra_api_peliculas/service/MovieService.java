package es.daw.extra_api_peliculas.service;

import es.daw.extra_api_peliculas.dto.response.MovieWithCastDto;
import es.daw.extra_api_peliculas.mapper.MovieMapper;
import es.daw.extra_api_peliculas.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public List<MovieWithCastDto>  getAllMoviesWithCast(){
        //return movieRepository.findAll()
        return movieRepository.findAllWithCast()
                .stream()
                .map(movieMapper::toMovieWithCastDto)
                //.collect(Collectors.toList());
                .toList();


    }
}
