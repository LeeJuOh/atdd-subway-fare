package nextstep.subway.application.dto.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class LineUpdateRequest {

    @NotBlank
    private final String name;

    @NotBlank
    private final String color;

    @PositiveOrZero
    private final Integer additionalFee;

    public LineUpdateRequest(String name, String color, Integer additionalFee) {
        this.name = name;
        this.color = color;
        this.additionalFee = additionalFee;
    }
}
