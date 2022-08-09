package starter.practicejpa.controller;


import lombok.Getter;
import lombok.Setter;

//validation 패키지 공부를 통해서 검증 로직을 짤 수 있다.
//form 데이터를
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
