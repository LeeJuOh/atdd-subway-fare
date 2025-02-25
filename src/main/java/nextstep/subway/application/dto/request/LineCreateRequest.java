package nextstep.subway.application.dto.request;


import lombok.Getter;

@Getter
public class LineCreateRequest {

    private final String name;

    private final String color;

    private final Long upStationId;

    private final Long downStationId;

    private final Long distance;
    private final Long duration;
    private final Integer additionalFee;

    public LineCreateRequest(String name, String color, Long upStationId, Long downStationId, Long distance,
        Long duration,
        Integer additionalFee) {
        this.name = name;
        this.color = color;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
        this.duration = duration;
        this.additionalFee = additionalFee;
    }
}
