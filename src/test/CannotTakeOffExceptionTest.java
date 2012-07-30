package test;

import main.exception.CannotTakeOffException;

import org.junit.Test;

public class CannotTakeOffExceptionTest {

  @Test
  public void testNoArgConsturctor() {
    new CannotTakeOffException();
  }

  @Test
  public void testStringArgConstructor() {
    new CannotTakeOffException("BOOM!");
  }

  @Test
  public void testThrowableConstructor() {
    new CannotTakeOffException(new Throwable());
  }

  @Test
  public void testStringAndThrowableConstructor() {
    new CannotTakeOffException("BOOM!", new Throwable());
  }

}
