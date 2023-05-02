package heroapi.service;

import heroapi.exception.APIException;
import heroapi.model.api.APITeamAffiliation;
import heroapi.model.repository.HeroRepository;
import heroapi.model.repository.TeamAffiliationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class TeamAffiliationService {
    private TeamAffiliationRepository teamRepo;
    private HeroRepository heroRepo;

    @Autowired
    public TeamAffiliationService(TeamAffiliationRepository teamRepo, HeroRepository heroRepo) {
        this.teamRepo = teamRepo;
        this.heroRepo = heroRepo;
    }

    // TODO: add methods here

    public APITeamAffiliation createTeamAffiliation(APITeamAffiliation apiTeam) {
        // TODO: complete method
        if(Objects.isNull(apiTeam)) {
            log.info("Error: APITeam is null");
            throw new APIException("APITeam cannot be null");
        }

        return null;
    }
}
