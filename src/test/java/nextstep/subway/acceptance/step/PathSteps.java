package nextstep.subway.acceptance.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import nextstep.subway.domain.enums.PathSearchType;
import org.springframework.http.MediaType;

public class PathSteps {

    public static final String SUBWAY_PATH_BASE_PATH = "/paths";

    public static ExtractableResponse<Response> 지하철_경로_조회_요청(Long sourceStationId, Long targetStationId,
        PathSearchType searchType) {
        return RestAssured.given().log().all()
            .queryParam("source", sourceStationId)
            .queryParam("target", targetStationId)
            .queryParam("type", searchType.name())
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get(SUBWAY_PATH_BASE_PATH)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 지하철_경로_조회_요청(Long sourceStationId, Long targetStationId,
        PathSearchType searchType, String accessToken) {
        return RestAssured.given().log().all()
            .auth().oauth2(accessToken)
            .queryParam("source", sourceStationId)
            .queryParam("target", targetStationId)
            .queryParam("type", searchType.name())
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get(SUBWAY_PATH_BASE_PATH)
            .then().log().all()
            .extract();
    }

    public static List<Long> 지하철역_경로_조회_응답에서_역_아이디_목록_추출(ExtractableResponse<Response> 지하철_경로_조회_응답) {
        return 지하철_경로_조회_응답.jsonPath()
            .getList("stations.id", Long.class);
    }

    public static List<String> 지하철역_경로_조회_응답에서_역_이름_목록_추출(ExtractableResponse<Response> 지하철_경로_조회_응답) {
        return 지하철_경로_조회_응답.jsonPath()
            .getList("stations.name");
    }

    public static long 지하철역_경로_조회_응답에서_경로_거리_추출(ExtractableResponse<Response> 지하철_경로_조회_응답) {
        return 지하철_경로_조회_응답.jsonPath().getLong("distance");
    }

    public static long 지하철역_경로_조회_응답에서_경로_시간_추출(ExtractableResponse<Response> 지하철_경로_조회_응답) {
        return 지하철_경로_조회_응답.jsonPath().getLong("duration");
    }


    public static int 지하철역_경로_조회_응답에서_지하철_총_요금_추출(ExtractableResponse<Response> 지하철_경로_조회_응답) {
        return 지하철_경로_조회_응답.jsonPath().getInt("totalFare");
    }
}
