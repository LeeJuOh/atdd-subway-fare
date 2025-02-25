package nextstep.subway.unit;

import static nextstep.subway.fixture.LineFixture.이호선_색;
import static nextstep.subway.fixture.LineFixture.이호선_이름;
import static nextstep.subway.fixture.StationFixture.강남역_이름;
import static nextstep.subway.fixture.StationFixture.교대역_이름;
import static org.assertj.core.api.Assertions.catchThrowable;
import java.util.List;
import java.util.Optional;
import nextstep.subway.domain.entity.Line;
import nextstep.subway.domain.entity.Path;
import nextstep.subway.domain.entity.Section;
import nextstep.subway.domain.entity.Station;
import nextstep.subway.domain.entity.SubwayMap;
import nextstep.subway.domain.enums.PathSearchType;
import nextstep.subway.fixture.LineFixture;
import nextstep.subway.fixture.SectionFixture;
import nextstep.subway.fixture.StationFixture;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubwayMapTest {


    private Station 교대역;
    private Station 강남역;
    private Station 양재역;
    private Station 남부터미널역;
    private Line 이호선;
    private Line 신분당선;
    private Line 삼호선;

    Section 교대역_강남역_구간;
    Section 강남역_양재역_구간;
    Section 교대역_남부터미널역_구간;
    Section 남부터미널역_양재역_구간;


    /**
     * 교대역   --- 2호선, 10, 1 ----    강남역
     * |                            |
     * 3호선, 2, 10                  신분당선, 10, 1
     * |                            |
     * 남부터미널역  --- 3호선, 3, 10 ---   양재
     */
    void 이호선_삼호선_신분당선_노선의_구간_존재() {
        교대역 = StationFixture.giveOne(1L, 교대역_이름);
        강남역 = StationFixture.giveOne(2L, 강남역_이름);
        양재역 = StationFixture.giveOne(3L, "양재역");
        남부터미널역 = StationFixture.giveOne(4L, "남부터미널역");

        이호선 = LineFixture.giveOne(1L, 이호선_이름, 이호선_색, 1000);
        삼호선 = LineFixture.giveOne(2L, "3호선", "orange", 1500);
        신분당선 = LineFixture.giveOne(3L, "신분당선", "red", 2000);

        교대역_강남역_구간 = SectionFixture.giveOne(1L, 이호선, 교대역, 강남역, 10L, 1L);
        교대역_남부터미널역_구간 = SectionFixture.giveOne(2L, 삼호선, 교대역, 남부터미널역, 2L, 1L);
        남부터미널역_양재역_구간 = SectionFixture.giveOne(3L, 삼호선, 남부터미널역, 양재역, 3L, 10L);
        강남역_양재역_구간 = SectionFixture.giveOne(4L, 신분당선, 강남역, 양재역, 10L, 10L);

        이호선.addSection(교대역_강남역_구간);
        삼호선.addSection(교대역_남부터미널역_구간);
        삼호선.addSection(남부터미널역_양재역_구간);
        신분당선.addSection(강남역_양재역_구간);
    }

    @DisplayName("지하철 최단거리를 조회한다.")
    @Test
    void findShorPath() {
        // given
        이호선_삼호선_신분당선_노선의_구간_존재();
        SubwayMap subwayMap = new SubwayMap(List.of(이호선, 삼호선, 신분당선));

        // when
        Optional<Path> 교대역_양재역_최단경로 = subwayMap.findShortestPath(교대역, 양재역, PathSearchType.DISTANCE);

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            Path shortestPath = 교대역_양재역_최단경로.orElseThrow();
            softAssertions.assertThat(shortestPath.getPathStations()).containsExactly(교대역, 남부터미널역, 양재역);
            softAssertions.assertThat(shortestPath.getDistance()).isEqualTo(5L);
        });

    }

    @DisplayName("지하철 최단거리를 조회에 실패한다. - 출발역과 도착역이 같은 경우")
    @Test
    void findShorPathWithError() {
        // given
        이호선_삼호선_신분당선_노선의_구간_존재();
        SubwayMap subwayMap = new SubwayMap(List.of(이호선, 삼호선, 신분당선));

        // when
        Throwable catchThrowable = catchThrowable(() -> {
            subwayMap.findShortestPath(교대역, 교대역, PathSearchType.DISTANCE);
        });

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(catchThrowable).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Source and target stations are the same");
        });

    }

    @DisplayName("지하철 최단거리를 조회에 실패한다. - 출발역과 도착역이 연결이 되어 있지 않은 경우")
    @Test
    void findShorPathWithError2() {
        // given
        이호선_삼호선_신분당선_노선의_구간_존재();
        SubwayMap subwayMap = new SubwayMap(List.of(이호선, 신분당선));

        // when
        Optional<Path> 교대역_남부터미널역_경로 = subwayMap.findShortestPath(교대역, 남부터미널역, PathSearchType.DISTANCE);

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(교대역_남부터미널역_경로).isEmpty();

        });

    }

    @DisplayName("지하철 최단거리를 조회에 실패한다. - 존재하지 않은 출발역이나 도착역을 조회 할 경우")
    @Test
    void findShorPathWithError3() {
        // given
        이호선_삼호선_신분당선_노선의_구간_존재();
        SubwayMap subwayMap = new SubwayMap(List.of(이호선, 삼호선, 신분당선));
        Station 존재하지_않는_역 = StationFixture.giveOne(Long.MAX_VALUE, "폐쇄역");

        // when
        Optional<Path> 존재하지_않는_경로 = subwayMap.findShortestPath(존재하지_않는_역, 양재역, PathSearchType.DISTANCE);

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(존재하지_않는_경로).isEmpty();
        });
    }


    @DisplayName("최대 추가 요금을 가진 노선을 올바르게 반환한다.")
    @Test
    void getLineWithMaxFare() {
        // given
        이호선_삼호선_신분당선_노선의_구간_존재();
        SubwayMap subwayMap = new SubwayMap(List.of(이호선, 삼호선, 신분당선));
        Path 최단시간_경로 = subwayMap.findShortestPath(교대역, 양재역, PathSearchType.DURATION).orElseThrow();

        // when
        Line lineWithMaxFare = 최단시간_경로.getLineWithMaxFare().orElseThrow();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(lineWithMaxFare).isEqualTo(신분당선);
            softAssertions.assertThat(lineWithMaxFare.getAdditionalFee()).isEqualTo(2000);
        });
    }

}
