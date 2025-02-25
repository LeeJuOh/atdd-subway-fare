package nextstep.subway.acceptance;

import static nextstep.subway.acceptance.step.LineSteps.지하철_노선_생성_요청;
import static nextstep.subway.acceptance.step.LineSteps.지하철_노선_응답에서_노선_아이디_추출;
import static nextstep.subway.acceptance.step.PathSteps.지하철_경로_조회_요청;
import static nextstep.subway.acceptance.step.SectionSteps.지하철_구간_등록_요청;
import static nextstep.subway.acceptance.step.StationSteps.지하철_역_생성_요청;
import static nextstep.subway.acceptance.step.StationSteps.지하철역_응답에서_역_아이디_추출;
import static nextstep.subway.fixture.LineFixture.노선_생성_요청_본문;
import static nextstep.subway.fixture.SectionFixture.구간_등록_요청_본문;
import static nextstep.subway.fixture.StationFixture.강남역_생성_요청_본문;
import static nextstep.subway.fixture.StationFixture.교대역_생성_요청_본문;
import static nextstep.subway.fixture.StationFixture.지하철역_생성_요청_본문;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.acceptance.step.PathSteps;
import nextstep.subway.domain.enums.PathSearchType;
import nextstep.utils.context.AcceptanceTest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("지하철역 경로 조회 기능")
@AcceptanceTest
class PathAcceptanceTest {


    private Long 교대역_아이디;
    private Long 강남역_아이디;
    private Long 양재역_아이디;
    private Long 남부터미널역_아이디;
    private Long 이호선_아이디;
    private Long 신분당선_아이디;
    private Long 삼호선_아이디;

    /**
     * Given 지하철 노선들과 구간들이 존재하고
     * When 출발역과 도착역을 기준으로 경로를 조회하면
     * Then 최단거리를 기준으로 경로를 조회한다.
     */
    @DisplayName("지하철역 최단 거리 기준 경로를 조회한다.")
    @Test
    void getPaths() {
        // given
        이호선_삼호선_신분당선_노선의_구간_존재();

        // when
        ExtractableResponse<Response> 지하철_경로_조회_응답 = 지하철_경로_조회_요청(교대역_아이디, 양재역_아이디, PathSearchType.DISTANCE);
        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(지하철_경로_조회_응답.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(PathSteps.지하철역_경로_조회_응답에서_역_아이디_목록_추출(지하철_경로_조회_응답))
                .containsExactly(교대역_아이디, 남부터미널역_아이디, 양재역_아이디);
            softAssertions.assertThat(PathSteps.지하철역_경로_조회_응답에서_경로_거리_추출(지하철_경로_조회_응답))
                .isEqualTo(5L);
        });

    }

    /**
     * 교대역   --- 2호선, 10 ----    강남역
     * |                            |
     * 3호선, 2                   신분당선, 10
     * |                            |
     * 남부터미널역  --- 3호선, 3 ---   양재
     */
    private void 이호선_삼호선_신분당선_노선의_구간_존재() {
        교대역_아이디 = 지하철역_응답에서_역_아이디_추출(지하철_역_생성_요청(교대역_생성_요청_본문()));
        강남역_아이디 = 지하철역_응답에서_역_아이디_추출(지하철_역_생성_요청(강남역_생성_요청_본문()));
        양재역_아이디 = 지하철역_응답에서_역_아이디_추출(지하철_역_생성_요청(지하철역_생성_요청_본문("양재역")));
        남부터미널역_아이디 = 지하철역_응답에서_역_아이디_추출(지하철_역_생성_요청(지하철역_생성_요청_본문("남부터미널역")));

        이호선_아이디 = 지하철_노선_응답에서_노선_아이디_추출(지하철_노선_생성_요청(노선_생성_요청_본문("2호선", "green", 교대역_아이디, 강남역_아이디, 10L, 10L, 0L)));
        신분당선_아이디 = 지하철_노선_응답에서_노선_아이디_추출(지하철_노선_생성_요청(노선_생성_요청_본문("신분당선", "red", 강남역_아이디, 양재역_아이디, 10L, 10L, 0L)));
        삼호선_아이디 = 지하철_노선_응답에서_노선_아이디_추출(지하철_노선_생성_요청(노선_생성_요청_본문("삼호선", "orange", 교대역_아이디, 남부터미널역_아이디, 2L, 2L, 0L)));

        지하철_구간_등록_요청(삼호선_아이디, 구간_등록_요청_본문(남부터미널역_아이디, 양재역_아이디, 3L, 3L));
    }

}
