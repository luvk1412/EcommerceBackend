package application.exception;


import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();

    public ValidationErrorResponse() {
    }

    public ValidationErrorResponse(List<Violation> violations) {
        this.violations = violations;
    }
    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }
}