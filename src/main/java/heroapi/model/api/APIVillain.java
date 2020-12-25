package heroapi.model.api;

public class APIVillain {
    public Long villain_id;
    public String villain_name;
    public String real_identity;
    public String powers;
    public String weaknesses;

    public APIVillain() {
    }

    public APIVillain(Long id, String name, String identity, String powers, String weakness) {
        this.villain_id = id;
        this.villain_name = name;
        this.real_identity = identity;
        this.powers = powers;
        this.weaknesses = weakness;
    }
}
