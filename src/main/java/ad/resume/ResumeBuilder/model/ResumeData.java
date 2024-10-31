package ad.resume.ResumeBuilder.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeData {

    @NonNull
    private String applicantName;

    @NonNull
    private String experience;

    @NonNull
    private String projects;
}
