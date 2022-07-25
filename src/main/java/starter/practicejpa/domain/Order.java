package starter.practicejpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name ="order_id")
    private Long id;

//    order 와 member는 다대 일 관계
//    FK 이름이 member_id 가 됨.
//    연관관계 주인을 정해줘야함
//    ->엔티티 객체는 서로 양방햔 참조를 하여 양방형을 변경이 가능하지만,
//   데이터베이스의 FK는 member_id 밖에 없기 때문에 해당 값을 변경 할 수 있는
//  FK를 가지고 있는 order를 연관관계의 주인으로 생각하면 된다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //cascade 설정시,
    //오더만 persist 만 해줘도 orderitem도 persist 패치 해줌.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //일대일일 경우엔 access를 많이 하는 곳에 fk를 놓는다.
    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //Date 타입을 쓰게 되면 따로 어노테이션 구성을 해줘야함.
    private LocalDateTime orderDateTime; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //연관관계 메서드
    //양방향 연관관계 일 경우 로직에서 편하게 볼 수 있도록
    //둘 중에 control 하는 엔티티에 작성
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    /*
        다른 방식의 주문생성을 막기 위해서는,
        1. protected Order(){ }
        2. 어노테이션 NoArgsConstructor(access = AccessLevel.PROTECTED) 사용
     */



    //==연관관계 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem: orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDateTime(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직 ==//
    //주문 취소
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    //== 조회 로직 ==//
    //전체 주문 가격 조회
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem: orderItems){
            totalPrice +=orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
