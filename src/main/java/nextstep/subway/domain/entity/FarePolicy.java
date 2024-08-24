package nextstep.subway.domain.entity;

public abstract class FarePolicy {

    private FarePolicy next;

    public static FarePolicy chain(FarePolicy first, FarePolicy... chain) {
        FarePolicy head = first;
        for (FarePolicy nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    protected int applyNext(int fare) {
        if (next == null) {
            return fare;
        }
        return next.apply(fare);
    }

    protected abstract int apply(int fare);
}
