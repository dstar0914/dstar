package kr.side.dstar.response;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ResponseService {

    // 단일건 결과를 처리하는 메소드
    public <T> SingleResultResponse<T> getSingleResult(T data) {
        SingleResultResponse<T> result = new SingleResultResponse<>();

        result.setData(data);

        setSuccessResult(result);

        return result;
    }
    // 다중건 결과를 처리하는 메소드
    public <T> ListResultResponse<T> getListResult(List<T> list) {
        ListResultResponse<T> result = new ListResultResponse<>();
        result.setList(list);

        setSuccessResult(result);

        return result;
    }
    // 실패 결과만 처리하는 메소드
    public CommonResponse getFailResult(int code, String message, String path) {
        CommonResponse result = new CommonResponse();
        result.setSuccess(false);
        result.setCode(code);
        result.setTimestamp(new Date());
        result.setError(new ErrorResponse(code, message, path));

        return result;
    }
    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(CommonResponse result) {
        result.setSuccess(true);
        result.setCode(200);
        result.setMessage("success!!");
        result.setTimestamp(new Date());
    }
}
