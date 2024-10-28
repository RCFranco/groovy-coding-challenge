package validator

import messages.UserProfileUpdate
import messages.UserProfileUpdatePayload
import spock.lang.Specification

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class UserProfileUpdateValidatorTest extends Specification {

    UserProfileUpdateValidator validator = new UserProfileUpdateValidator()

    def "Valid UserProfileUpdate should pass validation"() {
        given:
        def payload = new UserProfileUpdatePayload(
                userId: 1,
                name: "John Doe",
                birthDate: Instant.now().minusSeconds(30 * 365 * 24 * 60 * 60).toEpochMilli(), // 30 years ago
                lastSeen: Instant.now().toEpochMilli(),
                gender: "male",
                interestedIn: "women"
        )
        def message = new UserProfileUpdate(payload)

        when:
        def result = validator.validate(message)

        then:
        result.success()
    }

    def "UserProfileUpdate with userId 0 should fail validation"() {
        given:
        def payload = new UserProfileUpdatePayload(
                userId: 0,
                name: "John Doe",
                birthDate: Instant.now().minusSeconds(30 * 365 * 24 * 60 * 60).toEpochMilli(),
                lastSeen: Instant.now().toEpochMilli(),
                gender: "male",
                interestedIn: "women"
        )
        def message = new UserProfileUpdate(payload)

        when:
        def result = validator.validate(message)

        then:
        !result.success()
        result.errors.contains("User ID must be greater than 0")
    }

    def "UserProfileUpdate with empty name should fail validation"() {
        given:
        def payload = new UserProfileUpdatePayload(
                userId: 1,
                name: "",
                birthDate: Instant.now().minusSeconds(30 * 365 * 24 * 60 * 60).toEpochMilli(),
                lastSeen: Instant.now().toEpochMilli(),
                gender: "male",
                interestedIn: "women"
        )
        def message = new UserProfileUpdate(payload)

        when:
        def result = validator.validate(message)

        then:
        !result.success()
        result.errors.contains("Name must not be null or empty")
    }

    def "UserProfileUpdate with null name should fail validation"() {
        given:
        def payload = new UserProfileUpdatePayload(
                userId: 1,
                name: null,
                birthDate: Instant.now().minusSeconds(30 * 365 * 24 * 60 * 60).toEpochMilli(),
                lastSeen: Instant.now().toEpochMilli(),
                gender: "male",
                interestedIn: "women"
        )
        def message = new UserProfileUpdate(payload)

        when:
        def result = validator.validate(message)

        then:
        !result.success()
        result.errors.contains("Name must not be null or empty")
    }

    def "UserProfileUpdate with future lastSeen should fail validation"() {
        given:
        def payload = new UserProfileUpdatePayload(
                userId: 1,
                name: "John Doe",
                birthDate: Instant.now().minusSeconds(30 * 365 * 24 * 60 * 60).toEpochMilli(),
                lastSeen: Instant.now().plusSeconds(60 * 60).toEpochMilli(), // 1 hour in the future
                gender: "male",
                interestedIn: "women"
        )
        def message = new UserProfileUpdate(payload)

        when:
        def result = validator.validate(message)

        then:
        !result.success()
        result.errors.contains("Last seen date must not be in the future")
    }

    def "UserProfileUpdate with invalid gender should fail validation"() {
        given:
        def payload = new UserProfileUpdatePayload(
                userId: 1,
                name: "John Doe",
                birthDate: Instant.now().minusSeconds(30 * 365 * 24 * 60 * 60).toEpochMilli(),
                lastSeen: Instant.now().toEpochMilli(),
                gender: "invalid_gender",
                interestedIn: "women"
        )
        def message = new UserProfileUpdate(payload)

        when:
        def result = validator.validate(message)

        then:
        !result.success()
        result.errors.contains("Gender must be 'male' or 'female'")
    }

    def "UserProfileUpdate with invalid interestedIn field should fail validation"() {
        given:
        def payload = new UserProfileUpdatePayload(
                userId: 1,
                name: "John Doe",
                birthDate: Instant.now().minusSeconds(30 * 365 * 24 * 60 * 60).toEpochMilli(),
                lastSeen: Instant.now().toEpochMilli(),
                gender: "male",
                interestedIn: "invalid_option"
        )
        def message = new UserProfileUpdate(payload)

        when:
        def result = validator.validate(message)

        then:
        !result.success()
        result.errors.contains("InterestedIn must be 'men', 'women', or 'both'")
    }

    def "UserProfileUpdate with birth date older than 100 years should fail validation"() {
        given:
        LocalDate date101YearsAgo = LocalDate.now().minusYears(101)
        long date101YearsAgoMillis = date101YearsAgo
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

        def payload = new UserProfileUpdatePayload(
                userId: 1,
                name: "John Doe",
                birthDate: date101YearsAgoMillis,
                lastSeen: Instant.now().toEpochMilli(),
                gender: "male",
                interestedIn: "women"
        )
        def message = new UserProfileUpdate(payload)

        when:
        def result = validator.validate(message)

        then:
        !result.success()
        result.errors.contains("Birth date must be within the last 100 years")
    }


    def "UserProfileUpdate with birth date exactly 100 years ago should pass validation"() {
        given:
        LocalDate hundredYearsAgoDate = LocalDate.now().minusYears(100)
        long hundredYearsAgoMillis = hundredYearsAgoDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

        def payload = new UserProfileUpdatePayload(
                userId: 1,
                name: "John Doe",
                birthDate: hundredYearsAgoMillis,
                lastSeen: Instant.now().toEpochMilli(),
                gender: "male",
                interestedIn: "women"
        )
        def message = new UserProfileUpdate(payload)

        when:
        def result = validator.validate(message)

        then:
        result.success()
    }
}
