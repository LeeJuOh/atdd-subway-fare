package nextstep.subway.domain.entity;

public class AgeFarePolicy extends FarePolicy {


    private static final int YOUTH_MIN_AGE = 13;
    private static final int YOUTH_MAX_AGE = 18;
    private static final double YOUTH_DISCOUNT_RATE = 0.2;
    private static final int YOUTH_DEDUCTION = 350;

    private static final int CHILD_MIN_AGE = 6;
    private static final int CHILD_MAX_AGE = 12;
    private static final double CHILD_DISCOUNT_RATE = 0.5;
    private static final int CHILD_DEDUCTION = 350;

    private final int age;

    public AgeFarePolicy(int age) {
        this.age = age;
    }

    @Override
    public int apply(int fare) {

        if (isChild(age)) {
            return applyNext(calculateDiscountedFare(fare, CHILD_DEDUCTION, CHILD_DISCOUNT_RATE));
        }
        if (isYouth(age)) {
            return applyNext(calculateDiscountedFare(fare, YOUTH_DEDUCTION, YOUTH_DISCOUNT_RATE));
        }
        return applyNext(fare);
    }

    private boolean isYouth(int age) {
        return age >= YOUTH_MIN_AGE && age <= YOUTH_MAX_AGE;
    }

    private boolean isChild(int age) {
        return age >= CHILD_MIN_AGE && age <= CHILD_MAX_AGE;
    }

    private int calculateDiscountedFare(int fare, int deduction, double discountRate) {
        int reducedFare = Math.max(fare - deduction, 0);
        int discount = (int) (reducedFare * discountRate);
        return fare - discount;
    }

}
