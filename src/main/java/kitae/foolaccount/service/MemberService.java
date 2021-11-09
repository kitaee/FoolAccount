package kitae.foolaccount.service;

import kitae.foolaccount.domain.Asset;
import kitae.foolaccount.domain.Member;
import kitae.foolaccount.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) {
        memberRepository.save(member);
        return member.getCount();
    }

    public Long register(Asset asset) {
        memberRepository.save(asset);
        return asset.getSequence();
    }

    public Optional<Asset> call(String id){
        return memberRepository.callById(id);
    }

    public List<Asset> findMembers(){
        return memberRepository.findAll();
    }
}