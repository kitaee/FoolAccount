package kitae.foolaccount.repository;

import kitae.foolaccount.domain.Asset;
import kitae.foolaccount.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;
    private static Map<Long, Asset> asset_store = new HashMap<>();
    private static long index = 0L;

    @Override
    public Member save(Member member) {
        member.setCount(++sequence);
        store.put(member.getCount(), member);
        return member;
    }

    @Override
    public Asset save(Asset asset) {
        asset.setCount(++index);
        asset_store.put(asset.getCount(),asset);
        return asset;
    }

    @Override
    public Optional<Asset> callById(String id) {
        return asset_store.values().stream()
                .filter(asset -> asset.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Asset> findAll() {
        return new ArrayList<>(asset_store.values());
    }

    @Override
    public Optional<Member> findById(String id) {   // 로그인 기능을 위한 아이디찾기
        return store.values().stream()
                .filter(member -> member.getId().equals(id))
                .findAny();
    }

    @Override
    public Optional<Member> findByPassword(String password) {  // 로그인 기능을 위한 비밀번호 찾기
        return store.values().stream()
                .filter(member -> member.getPassword().equals(password))
                .findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<Member> findByPhone(String phone) {
        return store.values().stream()
                .filter(member -> member.getPhone().equals(phone))
                .findAny();
    }

    @Override
    public Optional<Member> findByPassword_confirm_question(String password_confirm_question) {
        return store.values().stream()
                .filter(member -> member.getPassword_confirm_question().equals(password_confirm_question))
                .findAny();
    }

    @Override
    public Optional<Member> findByPassword_confirm_question_answer(String password_confirm_question_answer) {
        return store.values().stream()
                .filter(member -> member.getPassword_confirm_question_answer().equals(password_confirm_question_answer))
                .findAny();
    }
}