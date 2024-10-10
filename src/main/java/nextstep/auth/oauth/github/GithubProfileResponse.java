package nextstep.auth.oauth.github;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GithubProfileResponse {

    private String email;
    private int age;

    public GithubProfileResponse(String email, int age) {
        this.email = email;
        this.age = age;
    }
}
