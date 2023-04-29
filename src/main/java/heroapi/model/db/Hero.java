package heroapi.model.db;

import lombok.*;

import javax.persistence.*;

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
}
