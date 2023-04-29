package heroapi.model.db;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TeamAffiliation {
    @Id
    @GeneratedValue
    @Column(name="team_affiliation_id")
    private Long teamAffiliationId;
    @Column(name="team_name")
    private String teamName;

    @OneToMany(mappedBy = "teamAffiliation")
    private Set<Hero> heroes;

    @Override
    public String toString() {
        return String.format("Team Affiliation: %s, No. of Members: %s", teamName, heroes.size());
    }
}
