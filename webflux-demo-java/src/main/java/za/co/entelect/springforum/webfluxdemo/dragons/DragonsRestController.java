package za.co.entelect.springforum.webfluxdemo.dragons;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import za.co.entelect.springforum.webfluxdemo.dragons.repository.DragonRepository;

@RestController
@RequestMapping("/controller")
public class DragonsRestController {

    private final DragonRepository dragonRepository;

    public DragonsRestController(DragonRepository dragonRepository) {
        this.dragonRepository = dragonRepository;
    }

    @GetMapping("/dragons")
    public Flux<Dragon> getDragonsByLocation(@RequestParam(value = "location", required = false) String location) {
        if (location != null) {
            return dragonRepository.findByLocation(location);
        } else {
            return dragonRepository.findAll();
        }
    }

    @PostMapping("/dragons")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Dragon>> createDragon(@RequestBody Mono<Dragon> newDragon) {
        return newDragon.flatMap(dragonRepository::save)
                .map(dragon -> ResponseEntity.created(dragon.uri()).body(dragon));
    }

    @GetMapping("/dragons/{name}")
    public Mono<ResponseEntity<Dragon>> getDragonByName(@PathVariable String name) {
        return dragonRepository.findByNameEqualsIgnoreCase(name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
