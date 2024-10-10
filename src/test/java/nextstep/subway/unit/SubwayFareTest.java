package nextstep.subway.unit;

import static nextstep.subway.fixture.LineFixture.이호선_색;
import static nextstep.subway.fixture.LineFixture.이호선_이름;
import static nextstep.subway.fixture.StationFixture.강남역_이름;
import static nextstep.subway.fixture.StationFixture.교대역_이름;
import java.util.List;
import java.util.stream.Stream;
import nextstep.subway.domain.entity.Line;
import nextstep.subway.domain.entity.Path;
import nextstep.subway.domain.entity.Station;
import nextstep.subway.domain.entity.SubWayFare;
import nextstep.subway.domain.entity.SubwayMap;
import nextstep.subway.domain.enums.PathSearchType;
import nextstep.subway.fixture.LineFixture;
import nextstep.subway.fixture.SectionFixture;
import nextstep.subway.fixture.StationFixture;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SubwayFareTest {


    private final Station 교대역 = StationFixture.giveOne(1L, 교대역_이름);
    private final Station 강남역 = StationFixture.giveOne(2L, 강남역_이름);
    private final Station 양재역 = StationFixture.giveOne(3L, "양재역");
    private final Line 이호선 = LineFixture.giveOne(1L, 이호선_이름, 이호선_색, 0);
    private final Line 신분당선 = LineFixture.giveOne(2L, "신분당선", "red", 0);
    private SubWayFare subWayFare;


    @ParameterizedTest
    @MethodSource("defaultFareArguments")
    void 지하철_요금_계산_기본_운임비(long distance, int expectedFare) {
        // given
        이호선.addSection(SectionFixture.giveOne(1L, 이호선, 교대역, 강남역, distance, 1));
        SubwayMap subwayMap = new SubwayMap(List.of(이호선, 신분당선));
        Path path = subwayMap.findShortestPath(교대역, 강남역, PathSearchType.DISTANCE).get();
        subWayFare = new SubWayFare(path);

        // when
        int fare = subWayFare.getTotalFare();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(fare).isEqualTo(expectedFare);
        });
    }

    @ParameterizedTest
    @MethodSource("over10kmFareArguments")
    void 지하철_요금_계산_10km_초과(long distance, int expectedFare) {
        // given
        이호선.addSection(SectionFixture.giveOne(1L, 이호선, 교대역, 강남역, distance, 1));
        SubwayMap subwayMap = new SubwayMap(List.of(이호선, 신분당선));
        Path path = subwayMap.findShortestPath(교대역, 강남역, PathSearchType.DISTANCE).get();
        subWayFare = new SubWayFare(path);

        // when
        int fare = subWayFare.getTotalFare();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(fare).isEqualTo(expectedFare);
        });
    }

    @ParameterizedTest
    @MethodSource("over50kmFareArguments")
    void 지하철_요금_계산_50km_초과(long distance, int expectedFare) {
        // given
        이호선.addSection(SectionFixture.giveOne(1L, 이호선, 교대역, 강남역, distance, 1));
        이호선.addSection(SectionFixture.giveOne(1L, 이호선, 강남역, 양재역, distance, 1));
        SubwayMap subwayMap = new SubwayMap(List.of(이호선, 신분당선));
        Path path = subwayMap.findShortestPath(교대역, 강남역, PathSearchType.DISTANCE).get();
        subWayFare = new SubWayFare(path);

        // when
        int fare = subWayFare.getTotalFare();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(fare).isEqualTo(expectedFare);
        });
    }

    @ParameterizedTest
    @MethodSource("lienAdditionalFeeArguments")
    void 지하철_요금_계산_노선_추가금액(int additionalFee, int additionalFee2, int expectedFare) {
        // given
        이호선.updateAdditionalFee(additionalFee);
        이호선.addSection(SectionFixture.giveOne(1L, 이호선, 교대역, 강남역, 1L, 1));
        신분당선.updateAdditionalFee(additionalFee2);
        신분당선.addSection(SectionFixture.giveOne(1L, 신분당선, 강남역, 양재역, 1L, 1));
        SubwayMap subwayMap = new SubwayMap(List.of(이호선, 신분당선));
        Path path = subwayMap.findShortestPath(교대역, 양재역, PathSearchType.DISTANCE).get();
        subWayFare = new SubWayFare(path);

        // when
        int fare = subWayFare.getTotalFare();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(fare).isEqualTo(expectedFare);
        });
    }


    @ParameterizedTest
    @MethodSource("ageDiscountFareArguments")
    void 지하철_요금_계산_연령_할인(long distance, int age, int expectedFare) {
        // given
        이호선.addSection(SectionFixture.giveOne(1L, 이호선, 교대역, 강남역, distance, 1));
        SubwayMap subwayMap = new SubwayMap(List.of(이호선, 신분당선));
        Path path = subwayMap.findShortestPath(교대역, 강남역, PathSearchType.DISTANCE).get();
        subWayFare = new SubWayFare(path, age);

        // when
        int fare = subWayFare.getTotalFare();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(fare).isEqualTo(expectedFare);
        });
    }


    private static Stream<Arguments> defaultFareArguments() {
        return Stream.of(
            Arguments.of(0L, 1250),
            Arguments.of(5L, 1250),
            Arguments.of(9L, 1250),
            Arguments.of(10L, 1250)
        );
    }

    private static Stream<Arguments> over10kmFareArguments() {
        return Stream.of(
            Arguments.of(11L, 1350),
            Arguments.of(14L, 1350),
            Arguments.of(16L, 1450),
            Arguments.of(21L, 1550),
            Arguments.of(50L, 2050)
        );
    }

    private static Stream<Arguments> over50kmFareArguments() {
        return Stream.of(
            Arguments.of(51L, 1850),
            Arguments.of(58L, 1850),
            Arguments.of(59L, 1950),
            Arguments.of(65L, 1950),
            Arguments.of(66L, 1950),
            Arguments.of(67L, 2050)
        );
    }

    private static Stream<Arguments> ageDiscountFareArguments() {
        return Stream.of(
            Arguments.of(5L, 5, 1250),
            Arguments.of(5L, 6, 800),
            Arguments.of(5L, 12, 800),
            Arguments.of(5L, 13, 1070),
            Arguments.of(5L, 18, 1070),
            Arguments.of(5L, 19, 1250)
        );
    }

    private static Stream<Arguments> lienAdditionalFeeArguments() {
        return Stream.of(
            Arguments.of(100, 200, 1450),
            Arguments.of(100, 100, 1350),
            Arguments.of(200, 100, 1450)

        );
    }


}
