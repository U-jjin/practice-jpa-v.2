package starter.practicejpa.repository.order;

import lombok.Data;
import starter.practicejpa.domain.Address;
import starter.practicejpa.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderFlatDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDateTime;
    private Address address;
    private OrderStatus orderStatus;

    private String itemName;
    private int orderPrice;
    private int count;


    public OrderFlatDto(Long orderId, String name, LocalDateTime orderDateTime, Address address, OrderStatus orderStatus, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.name = name;
        this.orderDateTime = orderDateTime;
        this.address = address;
        this.orderStatus = orderStatus;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
