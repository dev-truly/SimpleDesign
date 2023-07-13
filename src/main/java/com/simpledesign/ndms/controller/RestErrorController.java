package com.simpledesign.ndms.controller;

import com.simpledesign.ndms.common.ErrorCode;
import com.simpledesign.ndms.common.Utils;
import com.simpledesign.ndms.dto.HeaderDto;
import com.simpledesign.ndms.dto.SetDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class RestErrorController implements ErrorController {
    @GetMapping("/error")
    public SetDto handleError(HttpServletRequest request) {
        SetDto setDto = new SetDto();
        Object errorRequestUri = request.getAttribute("javax.servlet.error.request_uri");
        try {
            Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//            log.info(RequestDispatcher);
            HeaderDto logErrorCode = HeaderDto.fromErrorCode(
                    Utils.getErrorCode(status.toString()
                    )
            );
            setDto.setHeader(
                HeaderDto.fromErrorCode(ErrorCode.NoOpenapiServiceError)
            );
            log.error("[{}] : {}", errorRequestUri, logErrorCode.toString());
        } catch (IllegalArgumentException e) {
            log.error("[{}] IllegalArgumentException : {}", errorRequestUri, e.getMessage());
            setDto.setHeader(HeaderDto.fromErrorCode(ErrorCode.ApplicationError));
        } catch (Exception e) {
            log.error("[{}] Exception : {}", errorRequestUri, e.getMessage());
            setDto.setHeader(HeaderDto.fromErrorCode(ErrorCode.ApplicationError));
        }

        return setDto;
    }
}
