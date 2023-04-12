package heroapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import heroapi.model.api.APIHero;
import heroapi.model.api.APIResponse;
import heroapi.model.api.APIVillain;
import heroapi.model.db.Hero;
import heroapi.model.db.Villain;
import heroapi.service.HeroService;
import heroapi.service.VillainService;

@RestController
public class HeroAPIController {
    private static final Logger logger = LoggerFactory.getLogger(HeroAPIController.class);
    private HeroService heroSerivce;
    private VillainService villainService;

    @Autowired
    public HeroAPIController(HeroService heroSerivce, VillainService villainService) {
        this.heroSerivce = heroSerivce;
        this.villainService = villainService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String welcome() {
        return "Welcome to HeroAPI, please see ReadMe.md for details on using this API!";
    }

    // Hero API methods

    @GetMapping(value = "/hero", produces = "application/json")
    public ResponseEntity<APIResponse<List<APIHero>>> getHero(
            @RequestParam(value = "name", required = false) String name) {
        APIResponse<List<APIHero>> apiResponse = new APIResponse<>(heroSerivce.getHero(name), HttpStatus.OK.value(),
                "Successfully retrieved heroes");

        logger.info("GET Hero Response:[status = {}, message = {}]", apiResponse.getStatusCode(), apiResponse.getMessage());
        
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/hero", consumes = "application/json")
    public ResponseEntity<APIResponse<APIHero>> createHero(@RequestBody APIHero hero) {
        APIResponse<APIHero> apiResponse = null;

        APIHero newHero = heroSerivce.createHero(hero);
        apiResponse = new APIResponse<>(newHero, HttpStatus.OK.value(), "Successfully added hero to database");
        
        logger.info("POST Hero Response:[status = {}, message = {}]", apiResponse.getStatusCode(), apiResponse.getMessage());
        
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/hero/{heroId}", consumes = "application/json")
    public ResponseEntity<APIResponse<APIHero>> updateHero(@RequestBody APIHero hero, @PathVariable String heroId) {
        APIResponse<APIHero> apiResponse = null;

        APIHero updateHero = heroSerivce.updateHero(heroId, hero);
        apiResponse = new APIResponse<>(updateHero, HttpStatus.OK.value(), "Successfully updated hero");
        
        logger.info("PUT Hero Response:[status = {}, message = {}]", apiResponse.getStatusCode(), apiResponse.getMessage());
        
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // Villain API methods

    @GetMapping(value = "/villain", produces = "application/json")
    public ResponseEntity<APIResponse<List<APIVillain>>> getVillain(
            @RequestParam(value = "name", required = false) String name) {
        APIResponse<List<APIVillain>> apiResponse = new APIResponse<>(villainService.getVillain(name),
                HttpStatus.OK.value(), "Successfully retrieved villains");

        logger.info("GET Villain Response:[status = {}, message = {}]", apiResponse.getStatusCode(), apiResponse.getMessage());
        
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/villain", consumes = "application/json")
    public ResponseEntity<APIResponse<APIVillain>> createVillain(@RequestBody APIVillain villain) {
        APIResponse<APIVillain> apiResponse = null;
   
        APIVillain newVillain = villainService.createVillain(villain);
        apiResponse = new APIResponse<>(newVillain, HttpStatus.OK.value(),
                "Successfully added villain to database");

        logger.info("POST Villain Response:[status = {}, message = {}]", apiResponse.getStatusCode(), apiResponse.getMessage());
        
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/villain/{villainId}", consumes = "application/json")
    public ResponseEntity<APIResponse<APIVillain>> updateVillain(@RequestBody APIVillain villain,
            @PathVariable String villainId) {
        APIResponse<APIVillain> apiResponse = null;

        APIVillain updateVillain = villainService.updateVillain(villainId, villain);
        apiResponse = new APIResponse<>(updateVillain, HttpStatus.OK.value(), "Successfully updated Villain");
        
        logger.info("PUT Villain Response:[status = {}, message = {}]", apiResponse.getStatusCode(), apiResponse.getMessage());

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
