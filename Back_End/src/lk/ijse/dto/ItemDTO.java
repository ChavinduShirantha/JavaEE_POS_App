package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:34 PM
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    String code;
    String description;
    double unitPrice;
    int qty;
}
