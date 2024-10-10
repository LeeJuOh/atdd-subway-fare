package nextstep.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;
import io.cucumber.java8.En;
import java.util.UUID;
import nextstep.auth.fixture.TokenFixture;
import nextstep.auth.steps.TokenSteps;
import nextstep.cucumber.AcceptanceContext;
import nextstep.member.acceptance.steps.MemberSteps;
import nextstep.member.fixture.MemberFixture;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenStepDef implements En {


    @Autowired
    private AcceptanceContext context;

    public TokenStepDef() {

        Given("{int} 연령의 로그인된 사용자가 존재하고", (Integer age) -> {
            String email = "member_a@email.com";
            String password = UUID.randomUUID().toString();
            MemberSteps.회원_생성_요청(MemberFixture.회원_생성_요청_본문(email, password, age));
            String token =  TokenSteps.토큰_생성_응답에서_토큰값_추출(TokenSteps.토근_생성_요청(TokenFixture.토근_생성_요청_본문(email, password)));
            context.store.put("accessToken", token);
        });
    }

}
