package kr.side.dstar.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SingleResultResponse<T> extends CommonResponse {

    private T data;
}
