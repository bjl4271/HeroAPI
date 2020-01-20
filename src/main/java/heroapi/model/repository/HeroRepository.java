package heroapi.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import heroapi.model.dao.Hero;

public interface HeroRepository extends CrudRepository<Hero, Long> {

	List<Hero> findByName(String name);
	List<Hero> findByRealIdentity(String real_identity);
}
