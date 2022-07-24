package starter.practicejpa.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import starter.practicejpa.domain.Member;
import starter.practicejpa.repository.MemberRepository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)  //JUNIT이랑 스프링러너랑 테스트할래 의미
@SpringBootTest
@Transactional
public class MemberServcieTest {

    @Autowired MemberServcie memberServcie;
    @Autowired MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    @Rollback(false) //트랜젝션 롤백 안하고 싶다면~!
    public void 회원가입() throws Exception{
        Member member = new Member();
        member.setName("ahn");

        Long memberId = memberServcie.join(member);

        em.flush(); //database에 영속성 컨텍스트 바뀐 사항을 디비에 적오ㅛㅇㅇ
        assertEquals(member, memberRepository.findOne(memberId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        Member member1 = new Member();
        member1.setName("ahn1");
        Member member2 = new Member();
        member2.setName("ahn1");

        memberServcie.join(member1);
        memberServcie.join(member2);
        /*
        try{
            memberServcie.join(member2); //예외가 발생해야 한다!
        } catch (IllegalStateException e) {
            return;
        }
          -> 이렇게 적으면 너무 지저분 하기 때문에 어노테이션 옵션을 사용하자

         */

        //then
        fail("예외가 발생했다.");
    }
}