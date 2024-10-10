package nextstep.subway.domain.entity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.Getter;

@Getter
public class Path {

    private final Sections sections;

    public Path(Sections sections) {
        this.sections = sections;
    }

    public List<Station> getPathStations(Station target) {
        List<Station> stations = this.getPathStations();
        if (!stations.isEmpty()) {
            Station sourceStation = stations.get(0);
            if (Objects.equals(sourceStation.getId(), target.getId())) {
                Collections.reverse(stations);
            }
        }
        return stations;
    }

    public Optional<Line> getLineWithMaxFare() {
        return this.getSections().getAllSections().stream()
            .max(Comparator.comparingInt(section -> section.getLine().getAdditionalFee()))
            .map(Section::getLine);
    }

    public List<Station> getPathStations() {
        return this.sections.getSortedStationsByUpDirection(true);
    }


    public long getDistance() {
        return this.sections.getDistance();
    }

    public long getDuration() {
        return this.sections.getDuration();
    }
}
