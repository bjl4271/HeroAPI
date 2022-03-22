package heroapi.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIVillain {
    private Long villainId;
    private String villainName;
    private String realIdentity;
    private String powers;
    private String weaknesses;
}
