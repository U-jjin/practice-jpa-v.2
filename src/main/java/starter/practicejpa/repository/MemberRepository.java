package starter.practicejpa.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import starter.practicejpa.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    /*
    1.
         @PersistenceContext
         private EntityManager em;

         @PersistenceContext -> @AutoWired로 변경 가능
         ==> 스프링 데이터 jpa 가 이 기능을 제공해주고 있기 때문이다.

     2.
     @AutoWired
     private EntityManager em;

     -> AutoWired가 가능해지면 변수 주입 -> 생성자 주입 -> 롬복 @RequiredArgsConstructor 사용 가능
     */

    private final EntityManager em;

//    엔티티 매니저 팩토리를 주입 하고 싶다면
//    @PersistenceUnit
//    private EntityManagerFactory emf;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class,id);
        //타입과 pk를 넣어주자.
    }

    public List<Member> findAll(){ // 엔티티 객체에 대한 쿼리라고 생각하면 된다. jpql 좀 더 공부~!
        return em.createQuery("select m from Member m",Member.class).getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name",name)
                .getResultList();

    }
}
