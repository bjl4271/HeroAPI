package heroapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import heroapi.model.db.TeamAffiliation;
import heroapi.model.repository.TeamAffiliationRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import heroapi.exception.APIException;
import heroapi.exception.ResourceNotFoundException;
import heroapi.model.api.APIHero;
import heroapi.model.db.Hero;
import heroapi.model.repository.HeroRepository;
import heroapi.util.APIMapper;

@Slf4j
@Service
public class HeroService {
    private HeroRepository heroRepo;
    private TeamAffiliationRepository teamRepo;

    @Autowired
    public HeroService(HeroRepository heroRepo, TeamAffiliationRepository teamRepo) {
        this.heroRepo = heroRepo;
        this.teamRepo = teamRepo;
    }

    public APIHero createHero(APIHero apiHero) throws APIException {
        // TODO: add null check here for APIHero
        if (Objects.nonNull(apiHero.getHeroId())) {
            throw new APIException("hero_id is auto-generated and cannot have a value");
        }

        Hero hero = Hero.builder()
                .heroId(apiHero.getHeroId())
                .realIdentity(apiHero.getRealIdentity())
                .name(apiHero.getHeroName())
                .powers(apiHero.getPowers())
                .teamAffiliation(getTeamByName(apiHero.getTeamAffiliation()))
                .build();
        heroRepo.save(hero);
        log.info("Created new hero: {} in database", hero);

        return APIMapper.convertHeroToAPIHero(hero);
    }

    public List<APIHero> getHero(String name) {
        List<APIHero> heroList = new ArrayList<>();

        if (Objects.isNull(name) || name.isBlank()) {
            log.info("Getting all heroes from database");
            heroRepo.findAll().forEach((hero) -> {
                heroList.add(APIMapper.convertHeroToAPIHero(hero));
            });
        } else {
            Hero hero = heroRepo.findByNameIgnoreCase(name);
            
            if(Objects.isNull(hero)) {
                String message = String.format("Hero with name %s does not exist", name);
                log.info(message);
                throw new ResourceNotFoundException(message);
            }
            
            log.info("Getting hero by name '{}' from database", name);
            heroList.add(APIMapper.convertHeroToAPIHero(hero));
        }
        
        return heroList;
    }

    public APIHero updateHero(String heroId, APIHero apiHero) throws APIException, ResourceNotFoundException {
        if (Objects.isNull(heroId) || heroId.isBlank()) {
            throw new APIException("heroId missing from URL path");
        }

        Hero hero = heroRepo.findById(Long.valueOf(heroId)).orElse(null);

        if (Objects.isNull(hero)) {
            String message = String.format("Hero with id %s not found", heroId);
            log.info(message);
            throw new ResourceNotFoundException(message);
        } else {
            hero.setWeaknesses(apiHero.getWeaknesses());
            hero.setPowers(apiHero.getPowers());
            hero.setName(apiHero.getHeroName());
            hero.setRealIdentity(apiHero.getRealIdentity());
            hero.setTeamAffiliation(getTeamByName(apiHero.getTeamAffiliation()));
            heroRepo.save(hero);
        }

        return APIMapper.convertHeroToAPIHero(hero);
    }

    private TeamAffiliation getTeamByName(String teamName) {
        TeamAffiliation team = StringUtils.isNotBlank(teamName) ? teamRepo.findByTeamNameIgnoreCase(teamName) : null;
        if(team == null) {
            String message = String.format("Team Affiliation with name: %s, does not exist. " +
                    "Please try again with different Team Affiliation name or create new team.", teamName);
            log.info(message);
            throw new ResourceNotFoundException(message);
        }
        return team;
    }
}
