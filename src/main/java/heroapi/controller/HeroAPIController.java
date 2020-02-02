package heroapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import heroapi.exception.APIException;
import heroapi.exception.ResourceNotFoundException;
import heroapi.model.api.APIHero;
import heroapi.model.api.APIResponse;
import heroapi.model.api.APIVillain;
import heroapi.model.dao.Hero;
import heroapi.model.dao.Villain;
import heroapi.service.HeroService;
import heroapi.service.VillainService;

@RestController
public class HeroAPIController {
	private static final Logger logger = LoggerFactory.getLogger(HeroAPIController.class);
	
	@Autowired
	private HeroService heroSerivce;
	
	@Autowired
	private VillainService villainService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String welcome() {
		return "Welcome to HeroAPI, please see ReadMe.md for details on using this API!";
	}
	
	// Hero API methods
	
	@RequestMapping(method = RequestMethod.GET,value = "/hero", produces="application/json")
	public ResponseEntity<APIResponse<List<Hero>>> getHero(@RequestParam(value="name", required=false) String name) {
		APIResponse<List<Hero>> apiResponse = new APIResponse<>(heroSerivce.getHero(name),
															HttpStatus.OK.value(), "Successfully retrieved heroes");
		
		logger.info("GET Hero Response:[status = {}, message = {}]", apiResponse.status_code, apiResponse.message);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/hero", consumes="application/json")
	public ResponseEntity<APIResponse<Hero>> createHero(@RequestBody APIHero hero) {
		APIResponse<Hero> apiResponse = null;
		
		try {
			Hero newHero = heroSerivce.createHero(hero);
			apiResponse = new APIResponse<>(newHero, HttpStatus.OK.value(), "Successfully added hero to database");
			logger.info("POST Hero Response:[status = {}, message = {}]", apiResponse.status_code, apiResponse.message);
		} catch (APIException e) {
			logger.error("Unable to create new Hero: {}", e.getMessage());
			apiResponse = new APIResponse<>(null, HttpStatus.BAD_REQUEST.value(), e.getMessage());
			return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/hero/{heroId}", consumes="application/json")
	public ResponseEntity<APIResponse<Hero>> updateHero(@RequestBody APIHero hero, @PathVariable String heroId) {
		APIResponse<Hero> apiResponse = null;
		
		try {
			Hero updateHero = heroSerivce.updateHero(heroId, hero);
			apiResponse = new APIResponse<>(updateHero, HttpStatus.OK.value(), "Successfully updated hero");
			logger.info("PUT Hero Response:[status = {}, message = {}]", apiResponse.status_code, apiResponse.message);
		}
		catch(APIException e) {
			logger.error("Unable to update Hero: {}", e.getMessage());
			apiResponse = new APIResponse<>(null, HttpStatus.BAD_REQUEST.value(), e.getMessage());
			return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		}
		catch(ResourceNotFoundException e) {
			logger.error("Unable to update Hero: {}", e.getMessage());
			apiResponse = new APIResponse<>(null, HttpStatus.NOT_FOUND.value(), e.getMessage());
			return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	// Villain API methods
	
	@RequestMapping(method = RequestMethod.GET, value = "/villain", produces="application/json")
	public ResponseEntity<APIResponse<List<Villain>>> getVillain(
																@RequestParam(value="name", required=false)
																String name)
	{
		APIResponse<List<Villain>> apiResponse = new APIResponse<>(villainService.getVillain(name),
														HttpStatus.OK.value(), "Successfully retrieved villains");
		
		logger.info("GET Villain Response:[status = {}, message = {}]", apiResponse.status_code, apiResponse.message);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/villain", consumes="application/json")
	public ResponseEntity<APIResponse<Villain>> createVillain(@RequestBody APIVillain villain) {
		APIResponse<Villain> apiResponse = null;
		
		try {
			Villain newVillain = villainService.createVillain(villain);
			apiResponse = new APIResponse<>(newVillain, HttpStatus.OK.value(),
																			"Successfully added villain to database");
			
			logger.info("POST Villain Response:[status = {}, message = {}]",
																		apiResponse.status_code, apiResponse.message);
		} catch(APIException e) {
			logger.error("Unable to create new Villain: {}", e.getMessage());
			apiResponse = new APIResponse<>(null, HttpStatus.BAD_REQUEST.value(), e.getMessage());
			return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/villain/{villainId}", consumes="application/json")
	public ResponseEntity<APIResponse<Villain>> updateVillain(@RequestBody APIVillain villain,
																@PathVariable String villainId) 
	{
		APIResponse<Villain> apiResponse = null;
		
		try {
			Villain updateVillain = villainService.updateVillain(villainId, villain);
			apiResponse = new APIResponse<>(updateVillain, HttpStatus.OK.value(), "Successfully updated Villain");
		}
		catch(APIException e) {
			logger.error("Unable to update Villain: {}", e.getMessage());
			apiResponse = new APIResponse<>(null, HttpStatus.BAD_REQUEST.value(), e.getMessage());
			return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		}
		catch(ResourceNotFoundException e) {
			logger.error("Unable to update Villain: {}", e.getMessage());
			apiResponse = new APIResponse<>(null, HttpStatus.NOT_FOUND.value(), e.getMessage());
			return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
}
