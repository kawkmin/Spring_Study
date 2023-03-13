package hello.itemservice.web.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ItemUpdateForm {

  @NotNull
  private Long id;

  @NotNull
  private String itemName;

  @NotNull
  @Range(min = 1000, max = 1000000)
  private Integer price;

  private Integer quantity;

}
