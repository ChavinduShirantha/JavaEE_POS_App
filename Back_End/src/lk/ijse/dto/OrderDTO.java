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
public class OrderDTO {
    private String oId;
    private String cusID;
    private String date;
    private String subTotal;
    private String discount;
}
