package za.co.entelect.springforum.validationdemo.persistence;

import org.springframework.stereotype.Component;
import za.co.entelect.springforum.validationdemo.Cat;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final CatRepository catRepository;

    public DataInitializer(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @PostConstruct
    public void init() {
        catRepository.save(CatEntity.from(new Cat("Marie", "White")));
        catRepository.save(CatEntity.from(new Cat("Tom", "Brown")));
    }
}
