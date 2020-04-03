package za.co.entelect.springforum.validationdemo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import za.co.entelect.springforum.validationdemo.persistence.CatEntity;
import za.co.entelect.springforum.validationdemo.persistence.CatRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CatController {

    private final CatRepository catRepository;

    public CatController(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @GetMapping("/cats")
    public List<CatEntity> getAllCats() {
        return catRepository.findAll();
    }

    @GetMapping("/cats/{id}")
    public CatEntity getCatById(@PathVariable Integer id) {
        return catRepository.findById(id)
                .orElseThrow(() -> new CatNotFoundException(id));
    }

    @PostMapping("/cats")
    @ResponseStatus(HttpStatus.CREATED)
    public CatEntity createCat(@RequestBody @Valid Cat cat) {
        return catRepository.save(CatEntity.from(cat));
    }

    @PostMapping("/cats/invalid")
    @ResponseStatus(HttpStatus.CREATED)
    public CatEntity createInvalidCat(@RequestBody @Valid InvalidCat cat) {
        return catRepository.save(CatEntity.from(cat));
    }

    @DeleteMapping("/cats/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        catRepository.deleteById(id);
    }
}
