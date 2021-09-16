package Utils;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Serializable {

    private Header header;

    private Map<Object, Object> body;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Header {
        private String token;
    }
    
}
