package terminal

import spock.lang.Specification
import spock.lang.Stepwise
import terminal.model.Product
import terminal.service.TerminalService

@Stepwise
class TerminalApplicationTest extends Specification {

    TerminalService terminalService = new TerminalService()

    def setup() {
        // Set pricing before each test
        terminalService.setPricing([
                new Product("A", 2.00, 4, 7.00),   // A: $2 each, 4 for $7
                new Product("B", 12.00),           // B: $12 each
                new Product("C", 1.25, 6, 6.00),   // C: $1.25 each, 6 for $6
                new Product("D", 0.15)             // D: $0.15 each
        ])
        terminalService.resetCart()  // Reset the cart for each test
    }

    def "Test: Scan ABCDABAA; Verify total is 32.40"() {
        when: "Scanning items ABCDABAA"
        scanItems(["A", "B", "C", "D", "A", "B", "A", "A"])

        then: "The total should be 32.40"
        terminalService.getTotal() == 32.40
    }

    def "Test: Scan CCCCCCC; Verify total is 7.25"() {
        when: "Scanning items CCCCCCC"
        scanItems(["C", "C", "C", "C", "C", "C", "C"])

        then: "The total should be 7.25"
        terminalService.getTotal() == 7.25
    }

    def "Test: Scan ABCD; Verify total is 15.40"() {
        when: "Scanning items ABCD"
        scanItems(["A", "B", "C", "D"])

        then: "The total should be 15.40"
        terminalService.getTotal() == 15.40
    }

    private void scanItems(List<String> items) {
        items.each { code ->
            terminalService.scan(code)
        }
    }
}
