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
import heroapi.model.api.APIVillain;
import heroapi.model.db.Villain;
import heroapi.model.repository.VillainRepository;
import heroapi.util.APIMapper;

@Service
public class VillainService {
    private static final Logger logger = LoggerFactory.getLogger(VillainService.class);
    private VillainRepository villainRepo;

    @Autowired
    public VillainService(VillainRepository villainRepo) {
        this.villainRepo = villainRepo;
    }

    public APIVillain createVillain(APIVillain apiVillain) throws APIException {
        if (Objects.nonNull(apiVillain.getVillainId())) {
            throw new APIException("villain_id is auto-generated and cannot have a value");
        }

        Villain villain = Villain.builder()
                .name(apiVillain.getVillainName())
                .weaknesses(apiVillain.getWeaknesses())
                .realIdentity(apiVillain.getRealIdentity())
                .powers(apiVillain.getPowers())
                .build();
        villainRepo.save(villain);
        logger.info("Created new Villain: {} in database", villain);

        return APIMapper.convertVillainToAPIVillain(villain);
    }

    public List<APIVillain> getVillain(String name) {
        List<APIVillain> villainList = new ArrayList<>();

        if (Objects.isNull(name) || name.isBlank()) {
            logger.info("Getting all villains from database");
            villainRepo.findAll().forEach((villain) -> {
                villainList.add(APIMapper.convertVillainToAPIVillain(villain));
            });
        } else {
            Villain villain = villainRepo.findByNameIgnoreCase(name);
            
            if(Objects.isNull(villain)) {
                String message = String.format("Villain with name %s does not exist", name);
                logger.info(message);
                throw new ResourceNotFoundException(message);
            }
            
            logger.info("Getting villain by name: {} from database", name);
            villainList.add(APIMapper.convertVillainToAPIVillain(villain));
        }
        return villainList;
    }

    public APIVillain updateVillain(String villainId, APIVillain apiVillain)
            throws APIException, ResourceNotFoundException {
        if (Objects.isNull(villainId) || villainId.isBlank()) {
            throw new APIException("villain_id is missing from URL path");
        }

        Villain villain = villainRepo.findById(Long.valueOf(villainId)).orElse(null);

        if (Objects.isNull(villain)) {
            String message = String.format("Villain with id %s not found in database", villainId);
            logger.info(message);
            throw new ResourceNotFoundException(message);
        } else {
            villain.setName(apiVillain.getVillainName());
            villain.setPowers(apiVillain.getPowers());
            villain.setWeaknesses(apiVillain.getWeaknesses());
            villain.setRealIdentity(apiVillain.getRealIdentity());
            villainRepo.save(villain);
        }

        return APIMapper.convertVillainToAPIVillain(villain);
    }
}
