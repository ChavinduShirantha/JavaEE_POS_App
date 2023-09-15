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
public class CustomerDTO {
    private String id;
    private String name;
    private String address;
    private double salary;
}
