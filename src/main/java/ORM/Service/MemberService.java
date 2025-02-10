package ORM.Service;

import ORM.domain.Member;
import ORM.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

//
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원가입
    public Long signUp(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        Optional<Member> find=memberRepository.findByName(member.getName());
        if(find.isPresent()){
            throw new IllegalStateException("이미 존재하는 회원입니다. ");
        }

    }


    // 멤버 전체 조회
    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    // 멤버 이름으로 조회
    @Transactional(readOnly = true)
    public Member findByname(String name) {
        Member find=memberRepository.findByName(name).get();
        return find;
    }

    // 멤버 아이디로 조회
    @Transactional(readOnly = true)
    public Member findById(Long id) {
        Optional<Member> find=memberRepository.findById(id);
        if(find.isPresent()){
            return find.get();
        }else throw new IllegalStateException("없음");
    }

}
