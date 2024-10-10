package nextstep.subway.domain.entity;

import java.util.Optional;

public class LineFarePolicy extends FarePolicy {


    private final Path path;

    public LineFarePolicy(Path path) {
        this.path = path;
    }

    @Override
    public int apply(int fare) {
        Optional<Line> shouldLine = path.getLineWithMaxFare();
        int extraFee = shouldLine
            .map(Line::getAdditionalFee)
            .orElse(0);
        return applyNext(fare + extraFee);
    }


}
