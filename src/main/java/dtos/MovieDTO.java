package dtos;

import entities.Movie;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link Movie}
 */
public class MovieDTO implements Serializable {
    private final Long id;
    private final String name;
    private final String duration;
    private final String location;
    private final String startDate;
    private final String startTime;

    public MovieDTO(Movie m) {
        this.id = m.getId();
        this.name = m.getName();
        this.duration = m.getDuration();
        this.location = m.getLocation();
        this.startDate = m.getStartDate();
        this.startTime = m.getStartTime();
    }

    public static List<MovieDTO> getDtos(List<Movie> movies) {
        return movies.stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "duration = " + duration + ", " +
                "location = " + location + ", " +
                "startDate = " + startDate + ", " +
                "startTime = " + startTime + ")";
    }
}