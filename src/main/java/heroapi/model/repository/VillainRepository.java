package heroapi.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import heroapi.model.db.Villain;

public interface VillainRepository extends CrudRepository<Villain, Long> {

	Villain findByNameIgnoreCase(String name);
	List<Villain> findByRealIdentity(String real_identity);
}
