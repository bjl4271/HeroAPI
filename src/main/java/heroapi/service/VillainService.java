package heroapi.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import heroapi.exception.APIException;
import heroapi.model.api.APIVillain;
import heroapi.model.dao.Villain;
import heroapi.model.repository.VillainRepository;

@Service
public class VillainService {
	private static final Logger logger = LoggerFactory.getLogger(VillainService.class);

	@Autowired
	private VillainRepository villainRepo;
	
	public Villain createVillain(APIVillain apiVillain) throws APIException {
		if(apiVillain.villain_id != null) {
			throw new APIException("villain_id is auto-generated and cannot have a value");
		}
		
		Villain villain = new Villain(apiVillain.villain_name, apiVillain.real_identity,
										apiVillain.powers, apiVillain.weaknesses);
		villainRepo.save(villain);
		logger.info("Created new Villain: {} in database", villain.toString());
		
		return villain;
	}
	
	public List<Villain> getVillain(String name) {
		List<Villain> villainList = new ArrayList<>();
		
		if(name == null || name.isBlank() || name.isEmpty()) {
			logger.info("Getting all villains from database");
			villainRepo.findAll().forEach(villainList::add);
		}
		else {
			logger.info("Getting all villains by name: {} from database", name);
			villainList.addAll(villainRepo.findByName(name));
		}
		return villainList;
	}
}
