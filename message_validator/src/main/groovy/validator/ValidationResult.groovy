package validator

class ValidationResult {

    final List<String> errors

    ValidationResult() {
        this(Collections.emptyList())
    }

    ValidationResult(List<String> errors) {
        this.errors = errors
    }

    boolean success() {
        this.errors.size() == 0
    }
}
