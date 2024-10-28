package terminal.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class Product {
    String code
    BigDecimal unitPrice
    Integer volumeQuantity
    BigDecimal volumePrice

    Product() {}

    @JsonCreator
    Product(
            @JsonProperty("code") String code,
            @JsonProperty("unitPrice") BigDecimal unitPrice,
            @JsonProperty("volumeQuantity") Integer volumeQuantity = null,
            @JsonProperty("volumePrice") BigDecimal volumePrice = null
    ) {
        this.code = code
        this.unitPrice = unitPrice
        this.volumeQuantity = volumeQuantity
        this.volumePrice = volumePrice
    }
}
