package main.exception;

public class CannotTakeOffException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CannotTakeOffException() {
    super();
  }

  public CannotTakeOffException(String message) {
    super(message);
  }

  public CannotTakeOffException(Throwable cause) {
    super(cause);
  }

  public CannotTakeOffException(String message, Throwable cause) {
    super(message, cause);
  }

}
