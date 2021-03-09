package za.co.entelect.springforum.webfluxdemo.dragons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.net.URI;

@Data
@AllArgsConstructor
@Table("dragons")
public class Dragon {
    @Id
    @JsonIgnore
    private Integer id;

    private final String name;
    private final String location;

    URI uri() {
        return URI.create("/dragons/" + getName());
    }
}
