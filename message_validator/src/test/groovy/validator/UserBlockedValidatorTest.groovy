package validator

import messages.UserBlocked
import messages.UserBlockedPayload
import spock.lang.Specification

class UserBlockedValidatorTest extends Specification {

    UserBlockedValidator validator = new UserBlockedValidator()

    def "Valid UserBlocked should pass validation"() {
        given:
        def payload = new UserBlockedPayload(1, 2)
        def message = new UserBlocked(payload)

        when:
        def result = validator.validate(message)

        then:
        result.success()
    }

    def "UserBlocked with actorUserId 0 should fail validation"() {
        given:
        def payload = new UserBlockedPayload(0, 2)
        def message = new UserBlocked(payload)

        when:
        def result = validator.validate(message)

        then:
        !result.success()
        result.errors.contains("Actor user ID must be greater than 0")
    }

    def "UserBlocked with blockedUserId 0 should fail validation"() {
        given:
        def payload = new UserBlockedPayload(1, 0)
        def message = new UserBlocked(payload)

        when:
        def result = validator.validate(message)

        then:
        !result.success()
        result.errors.contains("Blocked user ID must be greater than 0")
    }

    def "UserBlocked with identical actor and blocked user IDs should fail validation"() {
        given:
        def payload = new UserBlockedPayload(1, 1)
        def message = new UserBlocked(payload)

        when:
        def result = validator.validate(message)

        then:
        !result.success()
        result.errors.contains("Actor and blocked user IDs must not be the same")
    }
}
