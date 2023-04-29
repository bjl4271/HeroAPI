package heroapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    public HeroService(HeroRepository heroRepo) {
        this.heroRepo = heroRepo;
    }

    public APIHero createHero(APIHero apiHero) throws APIException {
        if (Objects.nonNull(apiHero.getHeroId())) {
            throw new APIException("hero_id is auto-generated and cannot have a value");
        }

        Hero hero = Hero.builder()
                .heroId(apiHero.getHeroId())
                .realIdentity(apiHero.getRealIdentity())
                .name(apiHero.getHeroName())
                .powers(apiHero.getPowers())
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
            String message = String.format("Hero with id %s not found in database", heroId);
            log.info(message);
            throw new ResourceNotFoundException(message);
        } else {
            hero.setWeaknesses(apiHero.getWeaknesses());
            hero.setPowers(apiHero.getPowers());
            hero.setName(apiHero.getHeroName());
            hero.setRealIdentity(apiHero.getRealIdentity());
            heroRepo.save(hero);
        }

        return APIMapper.convertHeroToAPIHero(hero);
    }
}
