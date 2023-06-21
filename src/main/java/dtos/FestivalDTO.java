package dtos;

import entities.Festival;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link Festival}
 */
public class FestivalDTO implements Serializable {
    private final Long id;
    private final String name;
    private final String city;
    private final String startDate;
    private final Integer duration;

    public FestivalDTO(Festival f) {
        this.id = f.getId();
        this.name = f.getName();
        this.city = f.getCity();
        this.startDate = f.getStartDate();
        this.duration = f.getDuration();
    }

    public static List<FestivalDTO> getDtos(List<Festival> festivals) {
        return festivals.stream()
                .map(FestivalDTO::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getStartDate() {
        return startDate;
    }

    public Integer getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "city = " + city + ", " +
                "startDate = " + startDate + ", " +
                "duration = " + duration + ")";
    }
}