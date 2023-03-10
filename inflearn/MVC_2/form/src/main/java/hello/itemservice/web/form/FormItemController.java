package hello.itemservice.web.form;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class FormItemController {

  private final ItemRepository itemRepository;

  @ModelAttribute("regions") //자동으로 모델에 regions 이름으로 return 값이 벨류로 들어감
  public Map<String, String> regions() {
    Map<String, String> regions = new LinkedHashMap<>();
    regions.put("SEOUL", "서울");
    regions.put("BUSAN", "부산");
    regions.put("JEJU", "제주");
    return regions;
  }

  @ModelAttribute("itemTypes")
  public ItemType[] itemTypes() { // enum의 모든 정보 배열로 반환
    return ItemType.values();
  }

  @GetMapping
  public String items(Model model) {
    List<Item> items = itemRepository.findAll();
    model.addAttribute("items", items);
    return "form/items";
  }

  @GetMapping("/{itemId}")
  public String item(@PathVariable long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);

    Map<String, String> regions = new LinkedHashMap<>();
    regions.put("SEOUL", "서울");
    regions.put("BUSAN", "부산");
    regions.put("JEJU", "제주");
    model.addAttribute("regions", regions);

    return "form/item";
  }

  @GetMapping("/add")
  public String addForm(Model model) {

    model.addAttribute("item", new Item());

    Map<String, String> regions = new LinkedHashMap<>();
    regions.put("SEOUL", "서울");
    regions.put("BUSAN", "부산");
    regions.put("JEJU", "제주");
    model.addAttribute("regions", regions);

    return "form/addForm";
  }

  @PostMapping("/add")
  public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
    log.info("item.open={}", item.getOpen());
    log.info("item.regions={}", item.getRegions());
    log.info("item.itemType={}", item.getItemType());

    Item savedItem = itemRepository.save(item);
    redirectAttributes.addAttribute("itemId", savedItem.getId());
    redirectAttributes.addAttribute("status", true);
    return "redirect:/form/items/{itemId}";
  }

  @GetMapping("/{itemId}/edit")
  public String editForm(@PathVariable Long itemId, Model model) {
    Item item = itemRepository.findById(itemId);

    Map<String, String> regions = new LinkedHashMap<>();
    regions.put("SEOUL", "서울");
    regions.put("BUSAN", "부산");
    regions.put("JEJU", "제주");

    model.addAttribute("regions", regions);
    model.addAttribute("item", item);
    return "form/editForm";
  }

  @PostMapping("/{itemId}/edit")
  public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
    itemRepository.update(itemId, item);
    return "redirect:/form/items/{itemId}";
  }

}

