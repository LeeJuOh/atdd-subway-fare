package nextstep.subway.application.dto.request;


import lombok.Getter;

@Getter
public class LineCreateRequest {

    private String name;

    private String color;

    private Long upStationId;

    private Long downStationId;

    private Long distance;
    private Long duration;
    private Long additionalFee;

    public LineCreateRequest(String name, String color, Long upStationId, Long downStationId, Long distance,
        Long duration,
        Long additionalFee) {
        this.name = name;
        this.color = color;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
        this.duration = duration;
        this.additionalFee = additionalFee;
    }
}
