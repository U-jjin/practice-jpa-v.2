package starter.practicejpa.domain.item;

import lombok.Getter;
import lombok.Setter;
import starter.practicejpa.domain.Category;
import starter.practicejpa.exception.NotEnoughStockException;

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


    /*
        비즈니스 로직
        - setter 를 가지고 재고 수량 컨트롤 하는 것이 아니라.
        - 엔티티 안에서 필요한 메소드를 만드는 것이 응집력을 높이는 객체지향형 코드.
        1. 재고 수량 증가 로직
        2. 감소 로직
     */
    public void addStock(int quantity){
        this.stockQuantity +=quantity;
    }

    public void removeStock(int quantity){
       int restStock = this.stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
