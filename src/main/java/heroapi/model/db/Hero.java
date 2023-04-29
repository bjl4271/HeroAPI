package heroapi.model.db;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	
	@Override
	public String toString() {
		return String.format("Hero: %s, powers: %s, weaknesses: %s", name, powers, weaknesses);
	}
}
