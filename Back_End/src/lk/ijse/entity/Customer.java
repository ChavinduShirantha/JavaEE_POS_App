package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:32 PM
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String id;
    private String name;
    private String address;
    private double salary;
}
