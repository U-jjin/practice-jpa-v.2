package starter.practicejpa.api;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import starter.practicejpa.domain.Address;
import starter.practicejpa.domain.Order;
import starter.practicejpa.domain.OrderItem;
import starter.practicejpa.domain.OrderStatus;
import starter.practicejpa.repository.OrderRepository;
import starter.practicejpa.repository.OrderSearch;
import starter.practicejpa.repository.order.OrderFlatDto;
import starter.practicejpa.repository.order.OrderItemQueryDto;
import starter.practicejpa.repository.order.OrderQueryDto;
import starter.practicejpa.repository.order.OrderQueryRepository;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {


    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    /*

        V1. 엔티티 직접 노출
        - 엔티티가 변하면 API 스펙이 변한다.
        - 트랜잭션 안에서 지연 로딩 필요
        - Hibernate5Module 모듈 등록, LAZY=null 처리
        - 양방햔 연관관계 문제 ->@JsonIgnore

     */

    @GetMapping("api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        //iter 치고 tab하면 자동으로 바꿔준다.
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o->o.getItem().getName());

        }
        return all;
    }

    @GetMapping("api/v2/orders")
    public List<OrderDto> ordersV2(){
        /* V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
         * - 트랜잭션 안에서 지연 로딩 필요

         */
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        List<OrderDto> collect = orders
                                .stream()
                                .map(o->new OrderDto(o))
                                .collect(toList());
        return collect;
    }

    @GetMapping("api/v3/orders")
    public List<OrderDto> ordersV3(){
        List<Order> orders =orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream()
                .map(o-> new OrderDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset",defaultValue = "0")int offset,
                                        @RequestParam(value = "limit", defaultValue = "100") int limit){
        //ToOne 관계는 그냥 페치조인으로 가져온다.
        List<Order> orders =orderRepository.findAllWithMemberDelivery(offset,limit);


        List<OrderDto> result = orders.stream()
                .map(o-> new OrderDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("api/v4/orders")
    public List<OrderQueryDto> ordersV4(){
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("api/v5/orders")
    public List<OrderQueryDto> ordersV5(){
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("api/v6/orders")
    public List<OrderQueryDto> ordersV6(){
     List<OrderFlatDto> flats =    orderQueryRepository.findAllByDto_flat();

     return flats.stream()
             .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(),
                             o.getName(), o.getOrderDateTime(), o.getOrderStatus(), o.getAddress()),
                     mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                             o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
             )).entrySet().stream()
             .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                     e.getKey().getName(), e.getKey().getOrderDateTime(), e.getKey().getOrderStatus(),
                     e.getKey().getAddress(), e.getValue()))
             .collect(toList());

    }

    @Data
    static class OrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDateTime;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDateTime = order.getOrderDateTime();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems()
                    .stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(toList());
        }
    }

    @Data
    static public class OrderItemDto{

        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem){
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }
}



