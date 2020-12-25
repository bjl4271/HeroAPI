package heroapi;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import heroapi.controller.HeroAPIController;
import heroapi.exception.APIException;
import heroapi.exception.ResourceNotFoundException;
import heroapi.exception.RestExceptionHandler;
import heroapi.model.db.Hero;
import heroapi.model.db.Villain;
import heroapi.service.HeroService;
import heroapi.service.VillainService;

@TestInstance(Lifecycle.PER_CLASS)
public class HeroAPIControllerTest {
    private MockMvc mvc;
    private HeroAPIController heroController;
    private HeroService heroServiceMock;
    private VillainService villainServiceMock;
    private ObjectMapper objMapper;
    private Map<String, Object> apiHero;
    private Map<String, Object> apiVillain;

    @BeforeEach
    public void setupControllerTests() {
        heroServiceMock = mock(HeroService.class);
        villainServiceMock = mock(VillainService.class);
        heroController = new HeroAPIController(heroServiceMock, villainServiceMock);
        mvc = MockMvcBuilders.standaloneSetup(heroController).setControllerAdvice(RestExceptionHandler.class).build();
        objMapper = new ObjectMapper();
        setupTestData();
    }

    private void setupTestData() {
        apiHero = new HashMap<>();
        apiHero.put("hero_name", "Superman");
        apiHero.put("real_identity", "Clark Kent");
        apiHero.put("powers", "Super everything");
        apiHero.put("weaknesses", "Kryptonite");

        apiVillain = new HashMap<>();
        apiVillain.put("villain_name", "Captain Cold");
        apiVillain.put("real_identity", "Leonard Snart");
        apiVillain.put("powers", "Ice Control, Weapon Master");
        apiVillain.put("weaknesses", "Human");
    }

    @Test
    public void test_welcome() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string(not(emptyOrNullString())));
    }

    // Hero API tests

    @Test
    public void test_createHero() throws Exception {
        Hero hero = new Hero("Superman", "Clark Kent", "Super everything", "Kryptonite");

        when(heroServiceMock.createHero(any())).thenReturn(hero);

        mvc.perform(
                post("/hero").contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(apiHero)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.heroName", equalTo(hero.getHeroName())))
                .andExpect(jsonPath("$.data.powers", equalTo(hero.getPowers())))
                .andExpect(jsonPath("$.data.weaknesses", equalTo(hero.getWeaknesses())))
                .andExpect(jsonPath("$.data.realIdentity", equalTo(hero.getRealIdentity())));
    }

    @Test
    public void test_updateHero() throws Exception {
        Hero hero = new Hero("Superman", "Clark Kent", "Man of Steel", "Kryptonite");

        when(heroServiceMock.updateHero(anyString(), any())).thenReturn(hero);

        mvc.perform(
                put("/hero/1").contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(apiHero)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.heroName", equalTo(hero.getHeroName())))
                .andExpect(jsonPath("$.data.powers", equalTo(hero.getPowers())))
                .andExpect(jsonPath("$.data.weaknesses", equalTo(hero.getWeaknesses())))
                .andExpect(jsonPath("$.data.realIdentity", equalTo(hero.getRealIdentity())));
    }

    @Test
    public void test_getHero() throws Exception {
        Hero hero = new Hero("Superman", "Clark Kent", "Super everything", "Kryptonite");

        when(heroServiceMock.getHero("Superman")).thenReturn(List.of(hero));

        mvc.perform(get("/hero?name=Superman")).andExpect(status().isOk())
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

        mvc.perform(get("/hero")).andExpect(status().isOk()).andExpect(jsonPath("$.data[*]", hasSize(2)));
    }

    @Test
    public void test_getHeroNotFound() throws Exception {
        when(heroServiceMock.getHero(anyString())).thenThrow(ResourceNotFoundException.class);

        mvc.perform(get("/hero?name=asdfg")).andExpect(status().isNotFound());
    }

    @Test
    public void test_createHeroBadRequest() throws Exception {
        when(heroServiceMock.createHero(any())).thenThrow(APIException.class);

        mvc.perform(post("/hero").contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_updateHeroNotFound() throws Exception {
        when(heroServiceMock.updateHero(anyString(), any())).thenThrow(ResourceNotFoundException.class);

        mvc.perform(
                put("/hero/1").contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(apiHero)))
                .andExpect(status().isNotFound());
    }

    // Villain API tests

    @Test
    public void test_createVillain() throws Exception {
        Villain villain = new Villain("Captain Cold", "Leonard Snart", "Ice Control, Weapon Master", "Human");

        when(villainServiceMock.createVillain(any())).thenReturn(villain);

        mvc.perform(post("/villain").contentType(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(apiVillain))).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.villainName", equalTo(villain.getVillainName())))
                .andExpect(jsonPath("$.data.powers", equalTo(villain.getPowers())))
                .andExpect(jsonPath("$.data.weaknesses", equalTo(villain.getWeaknesses())))
                .andExpect(jsonPath("$.data.realIdentity", equalTo(villain.getRealIdentity())));
    }

    @Test
    public void test_updateVillain() throws Exception {
        Villain villain = new Villain("Captain Cold", "Leonard Snart", "really cold", "Snowman");

        when(villainServiceMock.updateVillain(anyString(), any())).thenReturn(villain);

        mvc.perform(put("/villain/1").contentType(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(apiVillain))).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.villainName", equalTo(villain.getVillainName())))
                .andExpect(jsonPath("$.data.powers", equalTo(villain.getPowers())))
                .andExpect(jsonPath("$.data.weaknesses", equalTo(villain.getWeaknesses())))
                .andExpect(jsonPath("$.data.realIdentity", equalTo(villain.getRealIdentity())));
    }

    @Test
    public void test_getVillain() throws Exception {
        Villain villain = new Villain("Captain Cold", "Leonard Snart", "Ice Control, Weapon Master", "Human");

        when(villainServiceMock.getVillain("Captain Cold")).thenReturn(List.of(villain));

        mvc.perform(get("/villain?name=Captain Cold")).andExpect(status().isOk())
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

        mvc.perform(get("/villain")).andExpect(status().isOk()).andExpect(jsonPath("$.data[*]", hasSize(2)));
    }

    @Test
    public void test_getVillainNotFound() throws Exception {
        when(villainServiceMock.getVillain(anyString())).thenThrow(ResourceNotFoundException.class);

        mvc.perform(get("/villain?name=sfgr")).andExpect(status().isNotFound());
    }

    @Test
    public void test_createVillainBadRequest() throws Exception {
        when(villainServiceMock.createVillain(any())).thenThrow(APIException.class);

        mvc.perform(post("/villain").contentType(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(apiVillain))).andExpect(status().isBadRequest());
    }

    @Test
    public void test_updateVillainNotFound() throws Exception {
        when(villainServiceMock.updateVillain(anyString(), any())).thenThrow(ResourceNotFoundException.class);

        mvc.perform(put("/villain/1").contentType(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(apiVillain))).andExpect(status().isNotFound());
    }
}
