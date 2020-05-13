package kr.side.dstar.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListResultResponse<T> extends CommonResponse {

    private List<T> list;
}
