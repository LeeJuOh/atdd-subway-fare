package nextstep.subway.domain.entity;

public class DistanceFarePolicy extends FarePolicy {


    public static final long DEFAULT_DISTANCE = 10L;
    public static final int OVER_10KM_DISTANCE = 10;
    public static final int OVER_50KM_DISTANCE = 50;
    public static final int OVER_10KM_FARE_DISTANCE = 5;
    public static final int OVER_50KM_FARE_DISTANCE = 8;
    public static final int OVER_10KM_FARE_AMOUNT = 100;
    public static final int OVER_50KM_FARE_AMOUNT = 100;

    private final Path path;

    public DistanceFarePolicy(Path path) {
        this.path = path;
    }


    @Override
    public int apply(int fare) {
        long distance = path.getDistance();
        if (distance > DEFAULT_DISTANCE) {
            fare += calculateOverFare(distance);
        }
        return applyNext(fare);
    }

    private int calculateOverFare(long distance) {
        if (distance > OVER_50KM_DISTANCE) {
            return (int) ((Math.ceil((distance - DEFAULT_DISTANCE - 1) / OVER_50KM_FARE_DISTANCE) + 1)
                * OVER_50KM_FARE_AMOUNT);
        }
        if (distance > OVER_10KM_DISTANCE) {
            return (int) ((Math.ceil((distance - DEFAULT_DISTANCE - 1) / OVER_10KM_FARE_DISTANCE) + 1)
                * OVER_10KM_FARE_AMOUNT);
        }
        return 0;
    }


}
