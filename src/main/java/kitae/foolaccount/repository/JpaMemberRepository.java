package kitae.foolaccount.repository;

import kitae.foolaccount.domain.Asset;
import kitae.foolaccount.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Asset save(Asset asset) {
        em.persist(asset);
        return asset;
    }

    @Override
    public Optional<Asset> callById(String id) {
        List<Asset> result = em.createQuery("select m from Asset m where m.id = :id", Asset.class)
                .setParameter("id", id)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Asset> findAll() {
        return em.createQuery("select m from Asset m", Asset.class)
                .getResultList();

    }

    @Override
    public Optional<Member> findById(String id) {
        List<Member> result = em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByPassword(String password) {
        List<Member> result = em.createQuery("select m from Member m where m.password = :password", Member.class)
                .setParameter("password", password)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByPhone(String phone) {
        List<Member> result = em.createQuery("select m from Member m where m.phone = :phone", Member.class)
                .setParameter("phone", phone)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByPassword_confirm_question(String password_confirm_question) {
        List<Member> result = em.createQuery("select m from Member m where m.password_confirm_question = :password_confirm_question", Member.class)
                .setParameter("password_confirm_question", password_confirm_question)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByPassword_confirm_question_answer(String password_confirm_question_answer) {
        List<Member> result = em.createQuery("select m from Member m where m.password_confirm_question_answer = :password_confirm_question_answer", Member.class)
                .setParameter("password_confirm_question_answer", password_confirm_question_answer)
                .getResultList();

        return result.stream().findAny();
    }


}
