package heroapi.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hero {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long hero_id;
	@Column(name="name")
	private String name;
	@Column(name="powers")
	private String powers;
	@Column(name="weaknesses")
	private String weaknesses;
	@Column(name="real_identity")
	private String realIdentity;
	
	protected Hero() {}
	
	public Hero(String name, String real_identity, String powers, String weaknesses) {
		this.name = name;
		this.realIdentity = real_identity;
		this.powers = powers;
		this.weaknesses = weaknesses;
	}
	
	public Long getHeroId() {
		return hero_id;
	}
	
	public String getHeroName() {
		return name;
	}
	
	public String getPowers() {
		return powers;
	}
	
	public String getWeaknesses() {
		return weaknesses;
	}

	public String getRealIdentity() {
		return realIdentity;
	}
	
	public void setHeroName(String name) {
		this.name = name;
	}
	
	public void setPowers(String powers) {
		this.powers = powers;
	}
	
	public void setWeaknesses(String weaknesses) {
		this.weaknesses = weaknesses;
	}
	
	public void setRealIdentity(String realIdentity) {
		this.realIdentity = realIdentity;
	}
	
	@Override
	public String toString() {
		return String.format("Hero: %s, powers: %s, weaknesses: %s", name, powers, weaknesses);
	}
}
