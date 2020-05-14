package kr.side.dstar.scrap.exception;

public class CScrapNotFoundException extends RuntimeException {

    public CScrapNotFoundException() {
        super();
    }

    public CScrapNotFoundException(String message) {
        super(message);
    }

    public CScrapNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
