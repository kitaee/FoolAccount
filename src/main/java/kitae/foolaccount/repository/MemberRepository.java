package kitae.foolaccount.repository;

import kitae.foolaccount.domain.Asset;
import kitae.foolaccount.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Asset save(Asset asset);
    Optional<Asset> callById(String id);
    List<Asset> findAll();
    Optional<Member> findById(String id);
    Optional<Member> findByPassword(String password);
    Optional<Member> findByName(String name);
    Optional<Member> findByPhone(String phone);
    Optional<Member> findByPassword_confirm_question(String password_confirm_question);
    Optional<Member> findByPassword_confirm_question_answer(String password_confirm_question_answer);
}