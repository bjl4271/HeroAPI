package heroapi.model.db;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Hero {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="hero_id")
	private Long heroId;
	@Column(name="name")
	private String name;
	@Column(name="powers")
	private String powers;
	@Column(name="weaknesses")
	private String weaknesses;
	@Column(name="real_identity")
	private String realIdentity;
	@Column(name="hero_type")
	private HeroType heroType;

	@ManyToOne
	@JoinColumn(name="team_affiliation_id")
	private TeamAffiliation teamAffiliation;
	
	@Override
	public String toString() {
		return String.format("Hero: %s, type: %s, team: %s, powers: %s, weaknesses: %s",
				name, heroType, teamAffiliation, powers, weaknesses);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hero hero = (Hero) o;
		return Objects.equals(heroId, hero.heroId)
				&& Objects.equals(name, hero.name)
				&& Objects.equals(powers, hero.powers)
				&& Objects.equals(weaknesses, hero.weaknesses)
				&& Objects.equals(realIdentity, hero.realIdentity)
				&& heroType == hero.heroType
				&& Objects.equals(teamAffiliation, hero.teamAffiliation);
	}

	@Override
	public int hashCode() {
		return Objects.hash(heroId, name, powers, weaknesses, realIdentity, heroType, teamAffiliation);
	}
}
