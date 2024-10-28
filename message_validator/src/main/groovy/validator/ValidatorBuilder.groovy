package validator

import java.util.function.Function

class ValidatorBuilder<MESSAGE> {

    class Validation<MESSAGE, TRANSFORMED_MESSAGE> {
        private final String message
        private final Function<MESSAGE, TRANSFORMED_MESSAGE> valueSupplier
        private final Function<TRANSFORMED_MESSAGE, Boolean> validation

        Validation(String message, Function<MESSAGE, TRANSFORMED_MESSAGE> valueSupplier, Function<TRANSFORMED_MESSAGE, Boolean> validation) {
            this.message = message
            this.valueSupplier = valueSupplier
            this.validation = validation
        }

        boolean validate(MESSAGE input) {
            this.validation.apply(valueSupplier.apply(input))
        }

        String getMessage() {
            message
        }
    }

    class MyValidator<MESSAGE> implements Validator<MESSAGE> {
        private final List<Validation<MESSAGE, ?>> validations

        MyValidator(List<Validation<MESSAGE, ?>> validations) {
            this.validations = validations
        }

        @Override
        ValidationResult validate(MESSAGE message) {
            List<String> errors = []

            validations.each { validation ->
                if (!validation.validate(message)) {
                    errors.add(validation.message)
                }
            }

            new ValidationResult(errors)
        }
    }

    private final List<Validation<MESSAGE, ?>> validations = []

    ValidatorBuilder<MESSAGE> add(String message, Function<MESSAGE, ?> valueSupplier, Function<?, Boolean> validation) {
        validations.add(new Validation<>(message, valueSupplier, validation))
        this
    }

    Validator<MESSAGE> build() {
        new MyValidator<>(validations)
    }
}
