Feature: 지하철역 경로 관련 기능

#  /**
#  * 교대역   ---  2호선, 10, 1  ----   강남역
#  * |                               |
#  * 3호선, 2, 20                 신분당선, 10, 1
#  * |                               |
#  * 남부터미널역 -- 3호선, 3, 30 ----   양재
#  */
  Background: 이호선, 삼호선, 신분당선 노선의 구간
    Given 지하철역들을 생성 요청하고
      | name   |
      | 교대역    |
      | 강남역    |
      | 양재역    |
      | 남부터미널역 |
    And 지하철 노선들을 생성 요청하고
      | name | color  | upStation | downStation | distance | duration |
      | 2호선  | green  | 교대역       | 강남역         | 10       | 1        |
      | 신분당선 | red    | 강남역       | 양재역         | 10       | 1        |
      | 3호선  | orange | 교대역       | 남부터미널역      | 2        | 20       |
    And 지하철 구간을 등록 요청하고
      | lineName | upStation | downStation | distance | duration |
      | 3호선      | 남부터미널역    | 양재역         | 3        | 30       |

  Scenario: 지하철역 최단 거리 경로를 조회한다.
    When "교대역"에서 "양재역"까지의 "최소 거리" 기준으로 경로 조회를 요청하면
    Then "최소 거리" 기준 "교대역,남부터미널역,양재역" 경로를 응답
    And 총 거리 5와 소요 시간 50을 함께 응답함
    And 이용 요금 1250도 함께 응답함

  Scenario: 지하철역 최단 시간 경로를 조회한다.
    When "양재역"에서 "교대역"까지의 "최소 시간" 기준으로 경로 조회를 요청하면
    Then "최소 시간" 기준 "양재역,강남역,교대역" 경로를 응답
    And 총 거리 20와 소요 시간 2을 함께 응답함
    And 이용 요금 1450도 함께 응답함


