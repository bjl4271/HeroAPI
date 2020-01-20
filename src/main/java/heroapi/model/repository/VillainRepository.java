package heroapi.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import heroapi.model.dao.Villain;

public interface VillainRepository extends CrudRepository<Villain, Long> {

	List<Villain> findByName(String name);
	List<Villain> findByRealIdentity(String real_identity);
}
