package hello.jdbc.repository.ex;

/**
 * MyDbException은 RuntimeException으로 바꾸기 위함이고,
 * MyDuplicateKeyException은 특정한 상황(키 값이 같을때)에 예외로 저장시켜, 추후 이 예외일 때 처리하기 위함
 */
public class MyDuplicateKeyException extends MyDbException {

  public MyDuplicateKeyException() {
    super();
  }

  public MyDuplicateKeyException(String message) {
    super(message);
  }

  public MyDuplicateKeyException(String message, Throwable cause) {
    super(message, cause);
  }

  public MyDuplicateKeyException(Throwable cause) {
    super(cause);
  }
}
