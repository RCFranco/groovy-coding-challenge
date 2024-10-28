package terminal.service

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import terminal.model.Product

import java.math.RoundingMode

@Service
class TerminalService {

    private Map<String, Product> pricing = [:]
    private Map<String, Integer> cart = [:].withDefault { 0 }

    @PostConstruct
    void initProducts() {
        setPricing([
                new Product("A", 2.00, 4, 7.00),   // A: $2 each, 4 for $7
                new Product("B", 12.00), // B: $12 each
                new Product("C", 1.25, 6, 6.00),   // C: $1.25 each, 6 for $6
                new Product("D", 0.15)   // D: $0.15 each
        ])
    }

    void setPricing(List<Product> products) {
        products.each { product ->
            pricing[product.code] = product
        }
    }

    // Scan a product by its code
    void scan(String productCode) {
        if (!pricing.containsKey(productCode)) {
            throw new IllegalArgumentException("Product $productCode not found")
        }
        cart[productCode] += 1
    }

    BigDecimal getTotal() {
        BigDecimal total = 0.0

        cart.each { code, quantity ->
            Product product = pricing[code]

            if (product.volumeQuantity && quantity >= product.volumeQuantity) {
                int volumePacks = (int) (quantity / product.volumeQuantity)
                int remainder = quantity % product.volumeQuantity
                total += (volumePacks * product.volumePrice) + (remainder * product.unitPrice)
            } else {
                total += quantity * product.unitPrice
            }
        }
        return total.setScale(2, RoundingMode.HALF_UP)
    }

    // Get all products with their pricing
    List<Product> getAllProducts() {
        return pricing.values().toList()
    }

    void resetCart() {
        cart.clear()
    }
}
