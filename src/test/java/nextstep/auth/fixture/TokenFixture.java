package nextstep.auth.fixture;

import java.util.Map;

public class TokenFixture {

    public static Map<String, String> 토근_생성_요청_본문(String email, String password) {
        return Map.of("email", email, "password", password);
    }

    public static Map<String, String> 깃허브_토근_생성_요청_본문(String code) {
        return Map.of("code", code);
    }
}
