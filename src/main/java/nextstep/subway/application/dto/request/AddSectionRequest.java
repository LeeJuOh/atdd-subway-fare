package nextstep.subway.application.dto.request;


import lombok.Getter;

@Getter
public class AddSectionRequest {

    private Long upStationId;
    private Long downStationId;
    private Long distance;
    private Long duration;

    public AddSectionRequest(Long upStationId, Long downStationId, Long distance, Long duration) {
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
        this.duration = duration;
    }
}
