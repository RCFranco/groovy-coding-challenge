package validator

interface Validator<T> {

    ValidationResult validate(T message)
}
