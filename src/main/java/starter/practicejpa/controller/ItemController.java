package starter.practicejpa.controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import starter.practicejpa.domain.item.Book;
import starter.practicejpa.domain.item.Item;
import starter.practicejpa.service.ItemService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public String create(BookForm form){
        Book book =new Book();

        //setter 제거 로직이 더 좋다. 생성자로
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
   }
//조회
   @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    //수정
    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";

    }

    @PostMapping("items/{itemId}/edit")
    //오브젝트 이름을 매칭해주는 어노테이션 ModelAttribute
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form){

        //유저가 아이템 변경 권한이 있는지 체크
        //데이터베이스에 들어갔다 나온 객체는 준영속상태의 객체
        //더이상 영속성컨텍스트가 관리하지 않는 객체.
        //문제점 : JPA가 관리하지않기 때문에 값을 변경하여도 db에 업데이트가 잃어나지 않는다.

//        Book book = new Book();
//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());
//        itemService.saveItem(book);
        // 웹 단계에서 엔티티를 생성하는 것보다, 이런식으로 넘겨주는 것이 좋다.
        // 넘겨줄 파라미터가 너무 많다면 ,DTO를 만들어주자
        itemService.updateItem(itemId,form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }
}
