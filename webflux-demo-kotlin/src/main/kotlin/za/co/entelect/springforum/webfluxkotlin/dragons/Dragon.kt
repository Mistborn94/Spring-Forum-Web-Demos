package za.co.entelect.springforum.webfluxkotlin.dragons

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("dragons")
data class Dragon(val name: String,
                  val location: String,
                  @Id @JsonIgnore val id: Int? = null)