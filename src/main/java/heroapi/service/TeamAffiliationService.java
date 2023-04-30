package heroapi.service;

import heroapi.model.repository.HeroRepository;
import heroapi.model.repository.TeamAffiliationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
