package ad.resume.ResumeBuilder.service;

import ad.resume.ResumeBuilder.model.ResumeData;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnhanceResumeUsingAI {

    private static final String OPEN_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String OPENAI_API_KEY = "YOUR_OPENAI_API_KEY";

    public Map enhanceResume(ResumeData resumeData) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization",
                "Bearer " + OPENAI_API_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        String prompt = "";
        requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(OPEN_API_URL, requestEntity, Map.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new Exception("OpenAI API call failed with status code: " + responseEntity.getStatusCodeValue());
        }
    }

}
