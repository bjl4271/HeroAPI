package heroapi.util;

import heroapi.model.api.APIHero;
import heroapi.model.api.APIVillain;
import heroapi.model.db.Hero;
import heroapi.model.db.Villain;

public class APIMapper {
    private APIMapper() {}
    
    public static APIHero convertHeroToAPIHero(Hero heroObj) {
        return APIHero.builder()
                .heroId(heroObj.getHeroId())
                .heroName(heroObj.getName())
                .powers(heroObj.getPowers())
                .realIdentity(heroObj.getRealIdentity())
                .weaknesses(heroObj.getWeaknesses())
                .teamAffiliation(heroObj.getTeamAffiliation().getTeamName())
                .build();
    }
    
    public static APIVillain convertVillainToAPIVillain(Villain villainObj) {
        return APIVillain.builder()
                .villainId(villainObj.getVillainId())
                .powers(villainObj.getPowers())
                .realIdentity(villainObj.getRealIdentity())
                .weaknesses(villainObj.getWeaknesses())
                .villainName(villainObj.getName())
                .build();
    }
}
