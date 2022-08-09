package starter.practicejpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import starter.practicejpa.domain.Member;
import starter.practicejpa.repository.MemberRepository;

import java.util.List;

/*
JPA의 모든 로직들은 트랜잭션 안에서 움직여야 한다.
 @Transactional 은 spring javax 가 있는데 spring 을 추천

 */

@Service
// 읽기 전용으로 해놓고 쓰기가 필요할 때 어노테이션 붙이기
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    /*
    1.
    @Autowired  //스프링 빈에 등록되어 있는 것을 인젝션
    private MemberRepository memberRepository;

   변경이 불가능하여 이렇게 보다는 setter 주입 방법으로 테스트 같은 거 할때 임의로 가짜 주입이 가능
   단점은 실제 런타임 돌아갈때 변경 될 때 문제가 생길 수 있으나, 거의 변경될 일이 없긴 하다.

    2.
    private MemberRepository memberRepository;
    @Autowired
    public  void setMemberRepository(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    요새 가장 많이 쓰이는 것은 생성자 주입 방식.
    객체를 생성할 때마다 직접 주입 해줘야 하기 때문에 놓치지 않을 수 있음.

    3.
    private final MemberRepository memberRepository;
//    @Autowired
    public MemberServcie(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    최신버전 스프링은 생성자가 하나만 있을 때는 autowired 가 없어도 주입해준다.

    4. 롬복의 어노테이션 @AllArgsConstructor 를 해주면 자동으로 생성자를 만들어 준다.

    5. @RequiredArgsConstructor -> final을 가진 변수들만으로 생성자를 만들어준다.
*/
    private final MemberRepository memberRepository; //  5번 추천

//    회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);  //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    //동시성 쓰레드에서 문제가 생길 수 있어서, 실무에서는 한번 더 최후에 방어가 필요
    //데이터베이스에서 name 을 unique 제약 조건으로 잡기
   private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회

    public List<Member> findMember(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }



}
