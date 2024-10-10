package nextstep.member.application.service;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import nextstep.auth.application.domain.CustomUserDetail;
import nextstep.auth.application.service.UserDetailService;
import nextstep.member.domain.Member;
import nextstep.member.domain.MemberDetailCustom;
import nextstep.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberDetailService implements UserDetailService {

    private final MemberRepository memberRepository;

    @Override
    public Optional<CustomUserDetail> findById(String id) {
        return memberRepository.findByEmail(id)
            .map(member -> new MemberDetailCustom(member.getEmail(), member.getPassword()));
    }


    @Transactional
    @Override
    public CustomUserDetail loadUserDetail(String id) {
        Member member = memberRepository.findByEmail(id)
            .orElseGet(() -> memberRepository.save(new Member(id, UUID.randomUUID().toString(), null)));
        return new MemberDetailCustom(member.getEmail(), member.getPassword());
    }

    @Override
    public CustomUserDetail loadUserDetail(String id, int age) {
        Member member = memberRepository.findByEmail(id)
            .orElseGet(() -> memberRepository.save(new Member(id, UUID.randomUUID().toString(), age)));
        return new MemberDetailCustom(member.getEmail(), member.getPassword());    }

}
