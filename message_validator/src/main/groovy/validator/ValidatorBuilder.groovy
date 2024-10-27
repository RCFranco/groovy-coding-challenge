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
    }

    class MyValidator<MESSAGE, TRANSFORMED_MESSAGE> implements Validator<MESSAGE> {

        private final List<Validation> validations

        MyValidator(List<Validation> validations) {
            this.validations = validations
        }

        ValidationResult validate(MESSAGE message) {
            List<String> errors = []

            for (Validation v : this.validations) {
                boolean result = v.validate(message)

                if (!result) {
                    errors.add(v.message)
                }
            }

            new ValidationResult(errors)
        }
    }

    private List<Validation> validations = []

    ValidatorBuilder<MESSAGE> add(String message, Function<MESSAGE, ?> valueSupplier, Function<?, Boolean> validation) {
        // TODO: Add the validation
        this.validations.add(new Validation(message, valueSupplier, validation))
        this
    }

    Validator<MESSAGE> build() {
        // TODO: Implement a validator given the added validations. Replace this with an actual implementation of validator.
        new MyValidator<>(this.validations)
    }
}
