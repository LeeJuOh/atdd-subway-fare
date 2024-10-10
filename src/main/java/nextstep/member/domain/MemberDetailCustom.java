package nextstep.member.domain;

import java.util.Objects;
import nextstep.auth.application.domain.CustomUserDetail;

public class MemberDetailCustom implements CustomUserDetail {

    private final String email;
    private final String password;

    public static final MemberDetailCustom EMPTY = new MemberDetailCustom(null, null);

    public MemberDetailCustom(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String getId() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean checkPassword(String password) {
        return Objects.equals(this.password, password);
    }

    @Override
    public boolean isEmpty() {
        return this == EMPTY || (this.email.isEmpty() && this.password.isEmpty());
    }


}
