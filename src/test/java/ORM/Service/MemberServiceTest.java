package ORM.Service;

import ORM.domain.Member;
import ORM.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception{
        Member member = new Member();
        member.setName("kim");

        Long savedId=memberService.signUp(member);

        assertEquals(member,memberService.findById(savedId)) ;

    }

    @Test
    public void 중복회원예외() throws Exception{
        Member member = new Member();
        member.setName("kim");

        Member member2=new Member();
        member2.setName("kim");

        memberService.signUp(member);
        try{
            memberService.signUp(member2);
        }catch (IllegalStateException e){
            return;     // 예외 발생해서 리턴되야함
        }

    }

}