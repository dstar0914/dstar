package kr.side.dstar.common;

import kr.side.dstar.response.CommonResponse;
import kr.side.dstar.response.ResponseService;
import kr.side.dstar.scrap.exception.CScrapNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-1, "Exception Error", request.getRequestURL().toString());
    }

    @ExceptionHandler(CScrapNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse memberNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-11, "Not Found Scrap data!", request.getRequestURL().toString());
    }
}
