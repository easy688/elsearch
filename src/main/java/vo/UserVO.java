package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liyuhui
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {
    private String name;
    private String address;
    private Integer age;
}
