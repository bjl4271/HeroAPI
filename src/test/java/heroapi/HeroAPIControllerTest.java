package heroapi;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import heroapi.controller.HeroAPIController;
import heroapi.model.dao.Hero;
import heroapi.model.dao.Villain;
import heroapi.service.HeroService;
import heroapi.service.VillainService;

@TestInstance(Lifecycle.PER_CLASS)
public class HeroAPIControllerTest {
	private MockMvc mvc;
	private HeroAPIController heroController;
	private HeroService heroServiceMock;
	private VillainService villainServiceMock;
	
	@BeforeEach
	public void setupControllerTests() {
		heroServiceMock = mock(HeroService.class);
		villainServiceMock = mock(VillainService.class);
		heroController = new HeroAPIController(heroServiceMock, villainServiceMock);
		mvc = MockMvcBuilders.standaloneSetup(heroController).build();
	}
	
    @Test
    public void test_welcome() throws Exception {
        mvc.perform(get("/"))
        	.andExpect(status().isOk())
        	.andExpect(content().string(not(emptyOrNullString())));
    }
    
    @Test
    public void test_getHero() throws Exception {
    	Hero hero = new Hero("Superman", "Clark Kent", "Super everything", "Kryptonite");
    	
    	when(heroServiceMock.getHero("Superman")).thenReturn(List.of(hero));
    	
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
    	
    	when(heroServiceMock.getHero(null)).thenReturn(heroList);
    	
    	mvc.perform(get("/hero"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.data[*]", hasSize(2)));
    }
    
    @Test
    public void test_getVillain() throws Exception {
    	Villain villain = new Villain("Captain Cold", "Leonard Snart", "Ice Control, Weapon Master", "Human");
    	
    	when(villainServiceMock.getVillain("Captain Cold")).thenReturn(List.of(villain));
    	
    	mvc.perform(get("/villain?name=Captain Cold"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.data[0].villainName", equalTo(villain.getVillainName())))
    		.andExpect(jsonPath("$.data[0].powers", equalTo(villain.getPowers())))
    		.andExpect(jsonPath("$.data[0].weaknesses", equalTo(villain.getWeaknesses())))
    		.andExpect(jsonPath("$.data[0].realIdentity", equalTo(villain.getRealIdentity())));
    }
    
    @Test
    public void test_getAllVillains() throws Exception {
    	List<Villain> villainList = List.of(
    				new Villain("Professor Zoom", "Eobard Thawne", "Negative Speed Force", "Insanity"),
    				new Villain("Captain Cold", "Leonard Snart", "Ice Control, Weapon Master", "Human"));
    	
    	when(villainServiceMock.getVillain(null)).thenReturn(villainList);
    	
    	mvc.perform(get("/villain"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.data[*]", hasSize(2)));
    }
}
