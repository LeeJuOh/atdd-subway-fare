package nextstep.cucumber;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class AcceptanceContext {

    public Map<String, Object> store = new HashMap<>();
    public ExtractableResponse<Response> response;

    public <T> T getValueFromStore(String name, Class<T> responseType) {
        return responseType.cast(this.store.get(name));
    }
}
