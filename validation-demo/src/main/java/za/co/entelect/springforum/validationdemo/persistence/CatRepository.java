package za.co.entelect.springforum.validationdemo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<CatEntity, Integer> {

}
