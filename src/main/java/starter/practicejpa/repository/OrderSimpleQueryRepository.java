package starter.practicejpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final; EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return (List<OrderSimpleQueryDto>) em.createQuery(
                "select new starter.practicejpa.repository.OrderSimpleQueryDto" +
                        "(o.id, m.name, o.orderDateTime, o.status, d.address) " +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d", OrderSimpleQueryDto.class)
                        .getResultList();
    }
}