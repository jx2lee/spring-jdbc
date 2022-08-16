package io.github.jx2lee.deepdivejdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import io.github.jx2lee.deepdivejdbc.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static io.github.jx2lee.deepdivejdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberRepositoryV1Test {
    private MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        // DriverManger 사용 시
        // DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        // repository = new MemberRepositoryV1(dataSource);

        // HikariPool
        HikariDataSource datasource = new HikariDataSource();
        datasource.setJdbcUrl(URL);
        datasource.setUsername(USERNAME);
        datasource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(datasource);
    }

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

        /*
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
             @Override
            public void call() throws Throwable {
                repository.findById(member.getMemberId());
            }
        }).isInstanceOf(NoSuchElementException.class);
        */
    }

}