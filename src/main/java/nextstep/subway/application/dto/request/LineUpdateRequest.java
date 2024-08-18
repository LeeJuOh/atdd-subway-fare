package nextstep.subway.application.dto.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class LineUpdateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String color;

    @PositiveOrZero
    private Long additionalFee;

    public LineUpdateRequest(String name, String color, Long additionalFee) {
        this.name = name;
        this.color = color;
        this.additionalFee = additionalFee;
    }
}
