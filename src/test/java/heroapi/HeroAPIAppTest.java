package heroapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = HeroAPIApp.class)
@AutoConfigureMockMvc
public class HeroAPIAppTest {
	
	@Autowired
	private MockMvc mvc;
	
    @Test
    public void welcome() throws Exception {
        mvc.perform(get("/"))
        	.andExpect(status().isOk())
        	.andExpect(content().string(not(emptyOrNullString())));
    }
    
    // TODO: add more unit tests for application
}
