package heroapi.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import heroapi.model.db.Hero;

public interface HeroRepository extends CrudRepository<Hero, Long> {

	Hero findByNameIgnoreCase(String name);
	List<Hero> findByRealIdentity(String real_identity);
}
