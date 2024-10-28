package terminal.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import terminal.service.TerminalService
import terminal.model.Product

@RestController
@RequestMapping('/api/pos')
class TerminalController {

    @Autowired
    private TerminalService terminal

    @PostMapping('/pricing')
    void setPricing(@RequestBody List<Product> products) {
        terminal.pricing = products
    }

    @PostMapping('/scan/{code}')
    ResponseEntity<String> scanProduct(@PathVariable('code') String code) {
        try {
            terminal.scan(code)
            return ResponseEntity.ok("Product scanned: $code" as String)
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        }
    }

    @GetMapping('/total')
    BigDecimal getTotal() {
        terminal.total
    }

    @GetMapping('/products')
    List<Product> getAllProducts() {
        terminal.allProducts
    }

    @GetMapping('/reset')
    void resetCart() {
        terminal.resetCart()
    }
}
