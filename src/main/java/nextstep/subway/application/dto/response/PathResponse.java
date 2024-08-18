package nextstep.subway.application.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.subway.domain.entity.Station;

@NoArgsConstructor
@Getter
public class PathResponse {

    @NoArgsConstructor
    @Getter
    public static class StationDto {

        private Long id;

        private String name;

        public StationDto(Station station) {
            this.id = station.getId();
            this.name = station.getName();
        }

    }

    private List<StationDto> stations;
    private long distance;
    private long duration;
    private int totalFare;

    public PathResponse(List<StationDto> stations, long distance, long duration, int totalFare) {
        this.stations = stations;
        this.distance = distance;
        this.duration = duration;
        this.totalFare = totalFare;
    }
}
