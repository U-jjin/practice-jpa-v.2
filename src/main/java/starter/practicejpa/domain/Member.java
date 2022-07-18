package starter.practicejpa.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

//    연관관계 거울이라면 mappedBy =(order 테이블에 있는 member) 넣어주기
//    여기에 값을 넣어준다고 해도 값이 변경되지 않음.
    @OneToMany(mappedBy="member")
    private List<Order> orders =new ArrayList<>();

}
