package ad.resume.ResumeBuilder.controller;

import ad.resume.ResumeBuilder.ResumeBuilderApplication;
import ad.resume.ResumeBuilder.model.ResumeData;
import ad.resume.ResumeBuilder.service.EnhanceResumeUsingAI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ResumeBuilderApplication.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class ResumeBuilderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    private EnhanceResumeUsingAI enhanceResumeUsingAI;

    @Autowired
    private ObjectMapper objectMapper;

    ResumeData resumeData;

    @BeforeEach
    public void setupRequest(){
        resumeData = new ResumeData();
        ResumeData resumeData = new ResumeData();
        resumeData.setApplicantName("testUser");
        resumeData.setExperience("testExperience");
        resumeData.setProjects("testProjects");
    }

    @Test
    public void testEnhanceResume_whenRequestIsNotNull_thenResponseIsOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/resumeBuilder/enhance-ResumeData")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resumeData))).andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.success").value(true))
                .andExpect((ResultMatcher) jsonPath("$.data").exists());
    }

    @Test
    public void testEnhanceResume_whenBodyIsNull_thenResponseIsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/resumeBuilder/enhance-ResumeData"))
                .andExpect(status().isBadRequest());
    }

    @Test
   public void testEnhanceResume_whenExceptionOcccurs_thenResponseIsInternalServerError() throws Exception {
       when(enhanceResumeUsingAI.enhanceResume(any(ResumeData.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/resumeBuilder/enhance-ResumeData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resumeData))).andExpect(status().isInternalServerError());
   }
}
