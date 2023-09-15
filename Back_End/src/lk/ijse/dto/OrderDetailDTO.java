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
public class OrderDetailDTO {
    private String oId;
    private String cusID;
    private String name;
    private String code;
    private String description;
    private String qty;
    private String price;
}
