package heroapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import heroapi.model.db.Hero;
import heroapi.model.db.Villain;
import heroapi.model.repository.HeroRepository;
import heroapi.model.repository.VillainRepository;

@SpringBootApplication
public class HeroAPIApp {
	private static final Logger logger = LoggerFactory.getLogger(HeroAPIApp.class);
	
    public static void main(String[] args) {
    	SpringApplication.run(HeroAPIApp.class, args);
    }
    
    @Bean
    public CommandLineRunner populateHeroDB(HeroRepository heroRepository) {
    	return (args) -> {
    		logger.info("Inserting some Heroes to database...");
    		heroRepository.save(new Hero("Batman", "Bruce Wayne", "Ace Detective, Martial Art skills", null));
    		heroRepository.save(new Hero("Superman", "Clark Kent", "Super everything", "Kryptonite"));
    		heroRepository.save(new Hero("Green Lantern", "Hal Jordan", "Green power ring", "Color yellow"));
    	};
    }
    
    @Bean
    public CommandLineRunner populateVillainDB(VillainRepository villainRepository) {
    	return (args) -> {
    		logger.info("Inserting some Villains to database...");
    		villainRepository.save(new Villain("Sinestro", "Thaal Sinestro", "Yellow power ring", "His Daughter"));
    		villainRepository.save(new Villain("Joker", "Unknown", "Genius intellect", "Batman"));
    	};
    }
}
