package starter.practicejpa.domain.item;

import lombok.Getter;
import lombok.Setter;
import starter.practicejpa.domain.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//싱글 테이블 전략을 잡아줘야 함.
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter @Setter
public abstract class Item {


    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    //상속 관계 맵핑
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories =new ArrayList<>();

}
