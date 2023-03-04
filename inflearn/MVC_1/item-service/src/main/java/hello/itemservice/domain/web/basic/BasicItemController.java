package hello.itemservice.domain.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

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

  @GetMapping("{itemID}")
  public String item(@PathVariable long itemID, Model model) {
    Item item = itemRepository.findById(itemID);
    model.addAttribute("item", item);
    return "basic/item";
  }

  @GetMapping("/add")
  public String addForm() {
    return "/basic/addForm";
  }

  //  @PostMapping("/add")
  public String addItemV1(@RequestParam String itemName, @RequestParam int price,
      @RequestParam Integer quantity, Model model) {
    Item item = new Item(itemName, price, quantity);

    itemRepository.save(item);
    model.addAttribute("item", item);

    return "basic/item";
  }

  //@PostMapping("/add")
  public String addItemV2(@ModelAttribute("item") Item item, Model model) {

    itemRepository.save(item);
    //model.addAttribute("item", item); //자동 추가, 생략 가능

    return "basic/item";
  }

  //@PostMapping("/add")
  public String addItemV3(@ModelAttribute Item item, Model model) { //생략은 클래스의 첫 글자만 소문자로 반환

    itemRepository.save(item);

    return "basic/item";
  }

  //@PostMapping("/add")
  public String addItemV4(Item item, Model model) { //@ModelAttribute 는 객체가 오면 생략 가능

    itemRepository.save(item);

    return "basic/item";
  }

  @PostMapping("/add")
  public String addItemV5(Item item) {

    itemRepository.save(item);

    return "redirect:/basic/items/" + item.getId(); //위의 item 메소드(Get)를 리턴하는 거랑 같아짐
  }

  @GetMapping("/{itemId}/edit")
  public String editForm(@PathVariable Long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);
    return "basic/editForm";
  }

  @PostMapping("/{itemId}/edit")
  public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
    itemRepository.update(itemId, item);
    return "redirect:/basic/items/{itemId}";
  }

  @PostConstruct
  public void init() {

    itemRepository.save(new Item("itemA", 1000, 10));
    itemRepository.save(new Item("itemB", 2000, 20));
  }
}
