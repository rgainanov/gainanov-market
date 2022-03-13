package ru.geekbrains.gainanov.market.api;

public class OrderItemDto {
    private Long id;
    private ProductDto productDto;
    private Long orderId;
    private int quantity;
    private int pricePerProduct;
    private int price;

    public OrderItemDto() {
    }

    public OrderItemDto(Long id, ProductDto productDto, Long orderId, int quantity, int pricePerProduct, int price) {
        this.id = id;
        this.productDto = productDto;
        this.orderId = orderId;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(int pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
