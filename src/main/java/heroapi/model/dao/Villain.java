package heroapi.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Villain {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long villain_id;
	@Column(name="name")
	private String name;
	@Column(name="powers")
	private String powers;
	@Column(name="weaknesses")
	private String weaknesses;
	@Column(name="real_identity")
	private String realIdentity;
	
	protected Villain() {}
	
	public Villain(String name, String real_identity, String powers, String weaknesses) {
		this.name = name;
		this.realIdentity = real_identity;
		this.powers = powers;
		this.weaknesses = weaknesses;
	}
	
	public Long getVillainId() {
		return villain_id;
	}
	
	public String getVillainName() {
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
	
	public void setVillainName(String name) {
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
		return String.format("Villain: %s, powers: %s, weaknesses: %s", name, powers, weaknesses);
	}
}
