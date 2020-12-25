package heroapi.util;

import heroapi.model.api.APIHero;
import heroapi.model.api.APIVillain;
import heroapi.model.db.Hero;
import heroapi.model.db.Villain;

public class APIMapper {
    private APIMapper() {}
    
    public static APIHero convertHeroToAPIHero(Hero heroObj) {
        APIHero apiHero = new APIHero();
        
        apiHero.hero_id = heroObj.getHeroId();
        apiHero.hero_name = heroObj.getHeroName();
        apiHero.powers = heroObj.getPowers();
        apiHero.real_identity = heroObj.getRealIdentity();
        apiHero.weaknesses = heroObj.getWeaknesses();
        
        return apiHero;
    }
    
    public static APIVillain convertVillainToAPIVillain(Villain villainObj) {
        APIVillain apiVillain = new APIVillain();
        
        apiVillain.villain_id = villainObj.getVillainId();
        apiVillain.powers = villainObj.getPowers();
        apiVillain.real_identity = villainObj.getRealIdentity();
        apiVillain.weaknesses = villainObj.getWeaknesses();
        apiVillain.villain_name = villainObj.getVillainName();
        
        return apiVillain;
    }
}
