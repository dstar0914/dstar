package kr.side.dstar.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CommonResponse {

    private int code;
    private boolean success;
    private Date timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorResponse error;
}
