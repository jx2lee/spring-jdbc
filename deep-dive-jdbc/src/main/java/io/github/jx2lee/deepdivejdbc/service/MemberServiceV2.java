package io.github.jx2lee.deepdivejdbc.service;

/*
  트랜잭션 - 파라미터 연동 & 풀을 고려한 연동
*/

import io.github.jx2lee.deepdivejdbc.domain.Member;
import io.github.jx2lee.deepdivejdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV2 {
    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        /*
         * 서비스 계측이 굉장히 복잡한 형태
         * Transaction 관리 + 비즈니스 로직 (Transaction logic >>> business logic
         */
        Connection con = dataSource.getConnection();
        try {
            con.setAutoCommit(false);
            bizLogic(con, fromId, toId, money);
            con.commit();

        } catch (Exception e) {
            con.rollback();
            throw new IllegalStateException(e);
        } finally {
            release(con);
        }
    }

    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(con, fromId);
        Member toMember = memberRepository.findById(con, toId);

        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }

    private void validation(Member member) {
        if (member.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체 중 오류가 발생하였습니다.");
        }
    }

    private void release(Connection con) throws SQLException {
        con.setAutoCommit(true);
        con.close();
    }
}
