package starter.practicejpa.repository;


import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import starter.practicejpa.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Order> findAll(OrderSearch orderSearch ){
/*
   // status name 의 값이 있을때만 이렇게 가져올 수 있다. 두 값이 null 이여도 가져 올 수 있는 동적 쿼리가 필요하다.
        return em.createQuery("select o from Order o join o.member m"+
                        " where o.status =:status and m.name like :name"
                        , Order.class)
                        .setParameter("status",orderSearch.getOrderStatus())
                        .setParameter("name",orderSearch.getMemberName())
                        .setMaxResults(1000) //최대 1000 건
                        .getResultList();

     1.jpql을 문자로 처리 한다.
 */
        String jpql =  "select o from Order o join o.member m";
        boolean isFirstCondition =true;

        //주문 상세 검색
        if(orderSearch.getOrderStatus() !=null){
            if(isFirstCondition){
                jpql +="where";
                isFirstCondition = false;
            } else{
                jpql +="and";
            }
            jpql +="o.status = :status";
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000);

        if(orderSearch.getOrderStatus() !=null){
            query = query.setParameter("status",orderSearch.getOrderStatus());
        }
        if(StringUtils.hasText(orderSearch.getMemberName())){
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }
    /*
        JPA Criteria
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> from = cq.from(Order.class);
        Join<Object, Object> m = from.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(from.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대1000건
        return query.getResultList();
    }
}
