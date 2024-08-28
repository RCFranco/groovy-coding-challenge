package validator

import spock.lang.Specification

class ValidatorTest extends Specification {
    def "nominal case"() {
        setup:
        def app

        when:
        def result

        then:
        !result
    }
}
