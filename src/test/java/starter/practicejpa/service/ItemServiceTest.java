package starter.practicejpa.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import starter.practicejpa.domain.item.Book;
import starter.practicejpa.repository.ItemRepository;
import starter.practicejpa.repository.MemberRepository;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품등록(){
        Book book = new Book();
        book.setName("책이름");
        book.setAuthor("안유진");

        itemService.saveItem(book);
        assertEquals(book, itemRepository.findOne(book.getId()));
    }

}