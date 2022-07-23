package starter.practicejpa.domain;

import lombok.Getter;
import lombok.Setter;
import starter.practicejpa.domain.item.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name="category_id")
    private Long id;

    private String name;

    //관계형 테이블은 1대다 다대1로 풀어낼 테이블이 필요
//   Many To Many 는 이렇게 딱 두가지 조인 테이블로만 해서 끝나서 다른 추가가 불가능
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name= "category_item",
               joinColumns = @JoinColumn(name="category_id"),
               inverseJoinColumns = @JoinColumn(name="item_id"))
    private List<Item> items =new ArrayList<>();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
