package io.github.jx2lee.deepdivejdbc.connection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


class ConnectionUtilTest {
    @Test
    void connectionTest() {
        // given
        Connection connection = ConnectionUtil.getConnection();

        // exepected
        Assertions.assertThat(connection).isNotNull();
    }
}