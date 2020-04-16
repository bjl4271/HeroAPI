package heroapi.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import heroapi.exception.APIException;
import heroapi.exception.ResourceNotFoundException;
import heroapi.model.api.APIHero;
import heroapi.model.dao.Hero;
import heroapi.model.repository.HeroRepository;

@Service
public class HeroService {
	private static final Logger logger = LoggerFactory.getLogger(HeroService.class);

	@Autowired
	private HeroRepository heroRepo;

	public Hero createHero(APIHero apiHero) throws APIException {
		if (apiHero.hero_id != null) {
			throw new APIException("hero_id is auto-generated and cannot have a value");
		}

		Hero hero = new Hero(apiHero.hero_name, apiHero.real_identity, apiHero.powers, apiHero.weaknesses);
		heroRepo.save(hero);
		logger.info("Created new hero: {} in database", hero.toString());

		return hero;
	}

	public List<Hero> getHero(String name) {
		List<Hero> heroList = new ArrayList<>();

		if (name == null || name.isBlank() || name.isEmpty()) {
			logger.info("Getting all heroes from database");
			heroRepo.findAll().forEach(heroList::add);
		} else {
			logger.info("Getting all heroes by name '{}' from database", name);
			heroList.addAll(heroRepo.findByName(name));
		}
		return heroList;
	}

	public Hero updateHero(String heroId, APIHero apiHero) throws APIException, ResourceNotFoundException {
		if (heroId == null || heroId.isBlank()) {
			throw new APIException("heroId missing from URL path");
		}

		Hero hero = heroRepo.findById(Long.valueOf(heroId)).orElse(null);

		if (hero == null) {
			logger.error("hero_id: {} not found in database", heroId);
			throw new ResourceNotFoundException("Hero with id: " + heroId + " not found in database");
		} else {
			hero.setHeroName(apiHero.hero_name);
			hero.setPowers(apiHero.powers);
			hero.setWeaknesses(apiHero.weaknesses);
			hero.setRealIdentity(apiHero.real_identity);
			heroRepo.save(hero);
		}

		return hero;
	}
}
