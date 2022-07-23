package starter.practicejpa.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

//값 타입은 변경이 불가능해야하며,
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //JPA에서 기본생성자가 기능상 필요하기 떄문에 명시해주어야함. 생선자를 쓰고 싶다면,
    protected Address(){}
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
