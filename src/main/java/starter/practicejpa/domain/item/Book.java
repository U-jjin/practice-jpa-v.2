package starter.practicejpa.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//상속 엔티티 구분 값
@Entity
@DiscriminatorValue("B")
@Getter @Setter
public class Book  extends Item{

    private String author;
    private String isbn;

}
