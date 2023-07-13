package com.simpledesign.ndms.common;

import lombok.Getter;
//import org.springframework.http.HttpStatus;

public enum ErrorCode {
    NomalService("00", null, "성공"),
    ApplicationError("01", "APPLICATION ERROR", "어플리케이션 에러"),
    TimeoutError("02", "TIMEOUT ERROR", "요청시간초과 에러"),
    InvalidRequestParameterError("10", "INVALID REQUEST PARAMETER ERROR", "잘못된 요청 파라메터 에러"),
    IndentRequestParameterError("11", "INDENT REQUEST PARAMETER ERROR", "필수 파라메터 에러"),
    NoOpenapiServiceError("12", "NO OPENAPI SERVICE ERROR", "해당 오픈 API서비스가 없거나 폐기됨"),
    InvalidMethodRequestError("13", "INVALID METHOD REQUEST ERROR", "잘못된 메소드 요청"),
    ServiceAccessDeniedError("20", "SERVICE ACCESS DENIED ERROR", "서비스 접근 거부"),
    LimitedNumberOfServiceRequestsExceedsError("22", "LIMITED NUMBER OF SERVICE REQUESTS EXCEEDS ERROR", "서비스 요청제한횟수 초과"),
    ServiceKeyIsNotRegisteredError("30", "SERVICE KEY IS NOT REGISTERED ERROR", "등록되지 않은 서비스키"),
    DeadlineHasExpiredError("31", "DEADLINE HAS EXPIRED ERROR", "기한 만료된 서비스키"),
    UnregisteredIpError("32", "UNREGISTERED IP ERROR", "등록되지 않은 IP"),
    NoApiKeyError("33", "NO API KEY ERROR", "API키가 없음"),
    DozenApiKeyError("34", "DOZEN API KEY ERROR", "여러 개의 API키"),
    UnknownError("99", "UNKNOWN ERROR", "기타 에러"),

    // ----------- SAT 전용 에러코드 NDMS API 제공에 사용되지 않음
    DBInsertError("101", "DB INSERT ERROR", "데이터 등록에 실패 했습니다."),
    DBUpdateError("102", "DB UPDATE ERROR", "데이터 수정에 실패 했습니다."),
    DBDeleteError("103", "DB DELETE ERROR", "데이터 삭제에 실패 했습니다."),

    BadRequest("400", "BAD REQUEST ERROR", "잘못 된 요청 입니다."),
    Forbidden("403", "FORBIDDEN ERROR", "요청 된 리소스에 대한 접근 권한이 없습니다."),
    PageNotFound("404", "PAGE NOT FOUND ERROR", "요청하신 페이지가 존재 하지 않습니다.");

    @Getter
    private String code;
    @Getter
    private String message;
    @Getter
    private String description;

    ErrorCode(String code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

}
