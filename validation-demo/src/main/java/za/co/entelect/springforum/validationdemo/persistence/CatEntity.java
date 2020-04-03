package za.co.entelect.springforum.validationdemo.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.entelect.springforum.validationdemo.Cat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String colour;

    public static CatEntity from(Cat cat) {
        return builder()
                .name(cat.getName())
                .colour(cat.getColour())
                .build();
    }
}
