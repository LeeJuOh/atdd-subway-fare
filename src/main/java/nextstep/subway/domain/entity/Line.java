package nextstep.subway.domain.entity;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String color;

    private long additionalFee;
    @Embedded
    private Sections sections;

    protected Line(String name, String color, long additionalFee) {
        this.name = name;
        this.color = color;
        this.sections = new Sections();
        this.additionalFee = additionalFee;
    }

    public static Line of(String name, String color, long additionalFee) {
        return new Line(name, color, additionalFee);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateColor(String color) {
        this.color = color;
    }

    public void updateAdditionalFee(long additionalFee) {
        this.additionalFee = additionalFee;
    }

    public void addSection(Section section) {
        section.setLine(this);
        this.sections.addSection(section);
    }

    public void removeSection(Station station) {
        this.sections.removeStation(station);
    }


    public List<Station> getAllStations() {
        return this.sections.getSortedStationsByUpDirection(true);
    }

    public List<Station> getAllStationsByDistinct() {
        return this.sections.getSortedStationsByUpDirection(true)
            .stream().distinct()
            .collect(Collectors.toList());
    }


}
