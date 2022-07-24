package starter.practicejpa.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import starter.practicejpa.domain.item.Item;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);   //신규 생성 객체
        }else{
            em.merge(item); // 기존 객체에 업데이트
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }


}
