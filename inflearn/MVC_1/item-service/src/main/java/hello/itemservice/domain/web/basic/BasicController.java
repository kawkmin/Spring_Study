package hello.itemservice.domain.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicController {

  private final ItemRepository itemRepository;

/*@Autowired 생성자가 하나면 생략 가능 / RequiredArgsConstructor가 대신함
  public BasicController(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }*/

  @GetMapping
  public String items(Model model) {
    List<Item> items = itemRepository.findAll();
    model.addAttribute("items", items);
    return "basic/items";
  }

  @PostConstruct
  public void init() {
    itemRepository.save(new Item("itemA", 1000, 10));
    itemRepository.save(new Item("itemB", 2000, 20));
  }
}
