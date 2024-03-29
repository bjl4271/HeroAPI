package heroapi.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIHero {
    private Long heroId;
    private String heroName;
    private String realIdentity;
    private String powers;
    private String weaknesses;
    private String teamAffiliation;
}
