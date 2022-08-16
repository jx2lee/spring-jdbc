package io.github.jx2lee.deepdivejdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                service.callThrows();
            }
        }).isInstanceOf(MyCheckedException.class);

        // lambda expressions
        Assertions.assertThatThrownBy(service::callThrows).isInstanceOf(MyCheckedException.class);
    }

    /*
     * Exception 을 상속받은 예외는 체크 예외
     */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /*
     * Checked 예외는 예외를 잡아 처리하거나, 던지거나 둘 중 하나를 필수로 선택
     */
    static class Service {
        Repository repository = new Repository();

        // 예외를 잡아 처리하는 코드
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                // 예외처리 로직
                log.info("예외처리, message={}", e.getMessage(), e);
            }
        }

        // 예외를 밖으로 던지는 코드
        public void callThrows() throws MyCheckedException {
            repository.call();
        }
    }

    static class Repository {
        public void call() throws MyCheckedException {
            throw new MyCheckedException("ex");
        }
    }

}
