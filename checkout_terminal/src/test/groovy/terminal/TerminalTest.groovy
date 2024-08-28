package terminal

import spock.lang.Specification

class TerminalTest extends Specification {
    def "application has a greeting"() {
        setup:
        def app = new Terminal()

        when:
        def result

        then:
        !result
    }
}
