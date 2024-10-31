package ad.resume.ResumeBuilder.controller;

import ad.resume.ResumeBuilder.model.ResumeData;
import ad.resume.ResumeBuilder.service.EnhanceResumeUsingAI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/resumeBuilder")
public class ResumeBuilderController {

    @Autowired
    private EnhanceResumeUsingAI enhanceResumeUsingAI;

    // take the request and bind it to a model
    // validate request.
    // send request to service layer to process data
    // generate response separate function to return.
    @RequestMapping("/enhance-ResumeData")
    public ResponseEntity<Map<String, Object>> enhanceResume(
            @Validated
            @RequestBody ResumeData resumeData) {
        try {
            System.out.println(resumeData);
            ResponseEntity<String> enhancedResume = (ResponseEntity<String>) enhanceResumeUsingAI.enhanceResume(resumeData);

            return ResponseEntity.ok(generateResponse(enhancedResume));
        } catch (Exception exception) {
            return (ResponseEntity<Map<String, Object>>) ResponseEntity.internalServerError();
        }
    }

    //this generates json response of processed data.
    private Map<String, Object> generateResponse(ResponseEntity<String> enhancedResume) {
        if(enhancedResume == null){
            throw new RuntimeException("Response from OPEN API is null");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", enhancedResume);
        response.put("message", "Successfully created");

        return response;
    }
}
