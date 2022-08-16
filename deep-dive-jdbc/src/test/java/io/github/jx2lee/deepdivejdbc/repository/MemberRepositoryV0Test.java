package io.github.jx2lee.deepdivejdbc.repository;

import io.github.jx2lee.deepdivejdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {
    private MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        // create
        Member member = new Member("jj", 1000000);
        repository.save(member);

        // find
        Member findMember = repository.findById(member.getMemberId());
        assertThat(findMember).isEqualTo(member);

        // updated: 1000000 -> 30000
        repository.update(member.getMemberId(), 30000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(30000);

        // delete
        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);

        // assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
        //      @Override
        //     public void call() throws Throwable {
        //         repository.findById(member.getMemberId());
        //     }
        // }).isInstanceOf(NoSuchElementException.class);
    }

}