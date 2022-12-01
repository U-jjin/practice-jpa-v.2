package starter.practicejpa.repository.order;


import lombok.Data;
import lombok.EqualsAndHashCode;
import starter.practicejpa.domain.Address;
import starter.practicejpa.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of="orderId")
public class OrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDateTime;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;


    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDateTime, OrderStatus orderStatus,Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.address = address;
    }

    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDateTime, OrderStatus orderStatus, Address address, List<OrderItemQueryDto> orderItems) {
        this.orderId = orderId;
        this.name = name;
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = orderItems;
    }
}
