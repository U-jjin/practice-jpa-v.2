package starter.practicejpa.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import starter.practicejpa.domain.Order;

import javax.persistence.EntityManager;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;
    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

//    public List<Order> findAll(OrderSearch orderSearch ){}
}
