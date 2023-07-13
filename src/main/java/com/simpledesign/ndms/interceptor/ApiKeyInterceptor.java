package com.simpledesign.ndms.interceptor;

import com.simpledesign.ndms.common.ErrorCode;
import com.simpledesign.ndms.common.ErrorCodeException;
import com.simpledesign.ndms.common.JsonUtils;
import com.simpledesign.ndms.common.ServiceKeyHolder;
import com.simpledesign.ndms.dto.HeaderDto;
import com.simpledesign.ndms.dto.SetDto;
import com.simpledesign.ndms.servicekey.dto.NdmsServiceInterceptorDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ApiKeyInterceptor implements HandlerInterceptor {
    @Autowired
    ModelMapper modelMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean returnFlag = false;
        String remoteAddr = null;
        String apiKey = null;

        try {
            remoteAddr = request.getRemoteAddr();

            String[] apiKeys = request.getParameterValues("serviceKey");

            if (apiKeys.length > 1) throw new ErrorCodeException(ErrorCode.DozenApiKeyError);

            apiKey = apiKeys[0];

            List<NdmsServiceInterceptorDto> serviceKeys = ServiceKeyHolder.getNdmsServiceKeys().stream()
                    .map(entity -> modelMapper.map(entity, NdmsServiceInterceptorDto.class))
                    .collect(Collectors.toList());

            for  (NdmsServiceInterceptorDto serviceKeyInfo: serviceKeys) {
                if (serviceKeyInfo.getNskIp().equals(remoteAddr)) {
                    if (apiKey == null || apiKey.isEmpty()) {
                        throw new ErrorCodeException(ErrorCode.NoApiKeyError);
                    }

                    if (!serviceKeyInfo.getServiceKey().equals(apiKey)) {
                        throw new ErrorCodeException(ErrorCode.ServiceKeyIsNotRegisteredError);
                    }

                    if (serviceKeyInfo.getIsApprove().equals("N")) {
                        throw new ErrorCodeException(ErrorCode.DeadlineHasExpiredError);
                    }

                    returnFlag = true;
                    break;
                }
            }

            if (!returnFlag) {
                throw new ErrorCodeException(ErrorCode.UnregisteredIpError);
            }
        }
        catch (ErrorCodeException e) {
            log.error("[ApiKeyInterceptor] ErrorCodeException : error_code: {}, connect_ip: {}, service_key: {}",
                    e.getErrorCode(),
                    remoteAddr,
                    apiKey
            );
            SetDto setDto = new SetDto();
            setDto.setHeader(HeaderDto.fromErrorCode(e.getErrorCode()));

            String json = JsonUtils.toJson(setDto);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(json);
        }

        return returnFlag;
    }
}
