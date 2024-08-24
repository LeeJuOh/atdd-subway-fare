package nextstep.subway.domain.entity;

public class SubWayFare {

    private static final int DEFAULT_DISTANCE_FARE = 1250;
    private final FarePolicy policy;
    private final int totalFare;

    public SubWayFare(Path path, int age) {
        FarePolicy policy = FarePolicy.chain(
            new DistanceFarePolicy(path),
            new LineFarePolicy(path),
            new AgeFarePolicy(age)
        );
        this.policy = policy;
        this.totalFare = calculateTotalFare();
    }

    public SubWayFare(Path path) {
        FarePolicy policy = FarePolicy.chain(
            new DistanceFarePolicy(path),
            new LineFarePolicy(path)
        );
        this.policy = policy;
        this.totalFare = calculateTotalFare();
    }

    private int calculateTotalFare() {
        return this.policy.apply(DEFAULT_DISTANCE_FARE);
    }

    public int getTotalFare() {
        return totalFare;
    }
}
