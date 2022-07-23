package starter.practicejpa.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name ="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch=FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    //어노테이션 Enumerated  사용 할때, default 가 ORDINAL
    // ready 0 comp1 이런식으로 이렇게 되면 ,혹시 같이 추가되면 망한다 그래서 String 으로 써야함
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status ;

}
