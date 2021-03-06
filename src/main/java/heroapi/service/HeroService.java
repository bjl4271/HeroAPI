package heroapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import heroapi.exception.APIException;
import heroapi.exception.ResourceNotFoundException;
import heroapi.model.api.APIHero;
import heroapi.model.db.Hero;
import heroapi.model.repository.HeroRepository;
import heroapi.util.APIMapper;

@Service
public class HeroService {
    private static final Logger logger = LoggerFactory.getLogger(HeroService.class);

    @Autowired
    private HeroRepository heroRepo;

    public APIHero createHero(APIHero apiHero) throws APIException {
        if (Objects.nonNull(apiHero.hero_id)) {
            throw new APIException("hero_id is auto-generated and cannot have a value");
        }

        Hero hero = new Hero(apiHero.hero_name, apiHero.real_identity, apiHero.powers, apiHero.weaknesses);
        heroRepo.save(hero);
        logger.info("Created new hero: {} in database", hero.toString());

        return APIMapper.convertHeroToAPIHero(hero);
    }

    public List<APIHero> getHero(String name) {
        List<APIHero> heroList = new ArrayList<>();

        if (Objects.isNull(name) || name.isBlank()) {
            logger.info("Getting all heroes from database");
            heroRepo.findAll().forEach((hero) -> {
                heroList.add(APIMapper.convertHeroToAPIHero(hero));
            });
        } else {
            Hero hero = heroRepo.findByName(name);
            
            if(Objects.isNull(hero)) {
                String message = String.format("Hero with name %s does not exist", name);
                logger.error(message);
                throw new ResourceNotFoundException(message);
            }
            
            logger.info("Getting hero by name '{}' from database", name);
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
            logger.error(message);
            throw new ResourceNotFoundException(message);
        } else {
            hero.setHeroName(apiHero.hero_name);
            hero.setPowers(apiHero.powers);
            hero.setWeaknesses(apiHero.weaknesses);
            hero.setRealIdentity(apiHero.real_identity);
            heroRepo.save(hero);
        }

        return APIMapper.convertHeroToAPIHero(hero);
    }
}
