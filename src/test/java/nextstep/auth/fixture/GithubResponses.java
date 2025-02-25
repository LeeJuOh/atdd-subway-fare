package nextstep.auth.fixture;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum GithubResponses {
    사용자1("1", "access_token_1", "email1@email.com", 6),
    사용자2("2", "access_token_2", "email2@email.com", 15);

    private final String code;
    private final String accessToken;
    private final String email;
    private final int age;

    GithubResponses(String code, String accessToken, String email, int age) {
        this.code = code;
        this.accessToken = accessToken;
        this.email = email;
        this.age = age;
    }

    public static GithubResponses lookUpByCode(String code) {
        return Arrays.stream(values())
            .filter(it -> Objects.equals(it.code, code))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    public static GithubResponses lookUpByToken(String accessToken) {
        return Arrays.stream(values())
            .filter(it -> Objects.equals(it.accessToken, accessToken))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

}
