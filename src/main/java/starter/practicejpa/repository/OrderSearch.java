package starter.practicejpa.repository;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import starter.practicejpa.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus; //주문 상태



}

