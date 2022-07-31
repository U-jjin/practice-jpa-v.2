package starter.practicejpa.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import starter.practicejpa.domain.Delivery;
import starter.practicejpa.domain.Member;
import starter.practicejpa.domain.Order;
import starter.practicejpa.domain.OrderItem;
import starter.practicejpa.domain.item.Item;
import starter.practicejpa.repository.ItemRepository;
import starter.practicejpa.repository.MemberRepository;
import starter.practicejpa.repository.OrderRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        /*
            delivery orderitem 은 리포지터리 save 하지 않았는데,
            그 이유는 orderRepository에서 어노테이션 cascade 어노테이션 덕분에 연속적으로 모두 persist가 일어남
            private owner인 경우에만 cascade 를 쓰는 것.
            만약, delivery orderitem이 다른곳에도 많이 쓰이면 사용XX
        */

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문정보 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    //취소

    @Transactional
    public void cancelOrder(Long orderID){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderID);
        //주문 취소
        order.cancel();  //JPA의 장점 : 변경내역 감지
    }

    //검색


}
