package devops.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MainTest {

    @Autowired
    private MockMvc mockMvc;

    /*
     Return auth view
    */
    @Test
    void authTest() throws Exception {
        String forwardedUrl = mockMvc.perform(MockMvcRequestBuilders.get("/auth/")).andExpect(status().isOk()).andReturn().getResponse().getForwardedUrl();
        assertEquals("/index.html", forwardedUrl);
    }

    /*
     Redirects to auth page
    */
    @Test
    void userTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(status().is3xxRedirection());
    }

    /*
     Redirects to auth page
    */
    @Test
    void moderatorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/moderator")).andExpect(status().is3xxRedirection());
    }

    /*
     Redirects to auth page
    */
    @Test
    void wrongTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/3.14door")).andExpect(status().is3xxRedirection());
    }
}
