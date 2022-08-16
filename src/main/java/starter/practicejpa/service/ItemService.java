package starter.practicejpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import starter.practicejpa.domain.item.Book;
import starter.practicejpa.domain.item.Item;
import starter.practicejpa.repository.ItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem (Item item){
        itemRepository.save(item);
    }
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockOfQuantity){
        //findOone 으로 찾아온 객체는 영속 상태이므로 자동으로,
        //flush 를 날려서 변경 상태를 적용해준다.
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockOfQuantity);

    }
    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne (Long itemId){
        return itemRepository.findOne(itemId);
    }

}
