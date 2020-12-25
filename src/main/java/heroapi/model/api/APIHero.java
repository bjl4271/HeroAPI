package heroapi.model.api;

public class APIHero {
	public Long hero_id;
	public String hero_name;
	public String real_identity;
	public String powers;
	public String weaknesses;
	
	public APIHero() {}
	
	public APIHero(Long id, String name, String identity, String powers, String weakness) {
	    this.hero_id = id;
	    this.hero_name = name;
	    this.real_identity = identity;
	    this.powers = powers;
	    this.weaknesses = weakness;
	}
}
