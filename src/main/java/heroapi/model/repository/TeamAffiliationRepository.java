package heroapi.model.repository;

import heroapi.model.db.TeamAffiliation;
import org.springframework.data.repository.CrudRepository;

public interface TeamAffiliationRepository extends CrudRepository<TeamAffiliation, Long> {
    TeamAffiliation findByNameIgnoreCase(String teamName);
}
