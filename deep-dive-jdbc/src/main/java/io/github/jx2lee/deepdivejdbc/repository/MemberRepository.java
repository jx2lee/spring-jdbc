package io.github.jx2lee.deepdivejdbc.repository;

import io.github.jx2lee.deepdivejdbc.domain.Member;

public interface MemberRepository {
    Member save(Member member);

    Member findById(String memberId);

    void update(String memberId, int money);

    void delete(String memberId);
}
