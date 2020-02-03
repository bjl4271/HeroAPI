package heroapi;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import heroapi.controller.HeroAPIController;
import heroapi.model.dao.Hero;
import heroapi.model.repository.HeroRepository;
import heroapi.model.repository.VillainRepository;
import heroapi.service.HeroService;
import heroapi.service.VillainService;

@WebMvcTest(HeroAPIController.class)
public class HeroAPIControllerTest {
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private HeroRepository heroRepo;
	
	@MockBean
	private HeroService heroService;
	
	@MockBean
	private VillainService villainService;
	
	@MockBean
	private VillainRepository villainRepo;

    @Test
    public void test_welcome() throws Exception {
        mvc.perform(get("/"))
        	.andExpect(status().isOk())
        	.andExpect(content().string(not(emptyOrNullString())));
    }
    
    // TODO: add more unit tests for controller methods
    
    @Test
    public void test_getHero() throws Exception {
    	Hero hero = new Hero("Superman", "Clark Kent", "Super everything", "Kryptonite");
    	
    	when(heroService.getHero("Superman")).thenReturn(List.of(hero));
    	
    	mvc.perform(get("/hero?name=Superman"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.data[0].heroName", equalTo(hero.getHeroName())))
    		.andExpect(jsonPath("$.data[0].powers", equalTo(hero.getPowers())))
    		.andExpect(jsonPath("$.data[0].weaknesses", equalTo(hero.getWeaknesses())))
    		.andExpect(jsonPath("$.data[0].realIdentity", equalTo(hero.getRealIdentity())));
    }
    
    @Test
    public void test_getAllHeroes() throws Exception {
    	List<Hero> heroList = List.of(new Hero("Superman", "Clark Kent", "Super everything", "Kryptonite"),
    								new Hero("Batman", "Bruce Wayne", "Ace Detective", "None"));
    	
    	when(heroService.getHero(null)).thenReturn(heroList);
    	
    	mvc.perform(get("/hero"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.data[*]", hasSize(2)));
    }
}
