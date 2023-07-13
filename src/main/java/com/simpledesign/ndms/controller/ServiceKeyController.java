package com.simpledesign.ndms.controller;

import com.simpledesign.ndms.common.ErrorCode;
import com.simpledesign.ndms.common.ServiceKeyHolder;
import com.simpledesign.ndms.dto.HeaderDto;
import com.simpledesign.ndms.servicekey.dto.NdmsServiceKeyDto;
import com.simpledesign.ndms.servicekey.entity.NdmsServiceKey;
import com.simpledesign.ndms.servicekey.service.ServiceKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("")
public class ServiceKeyController {
    @Autowired
    ServiceKeyService serviceKeyService;

    // GET 요청 "/service-key"에 대한 처리
    @GetMapping("/service-key")
    public ModelAndView viewServiceKey(
            ModelAndView mv) {
        mv.setViewName("views/approve");
        return mv;
    }

    @GetMapping("/service-key/helper")
    public ModelAndView viewServiceKeyHelper(
            ModelAndView mv) {
        mv.setViewName("views/ndms_service_key_approve");
        return mv;
    }

    // GET 요청 "/get-ip-service-key"에 대한 처리
    @ResponseBody
    @GetMapping("/get-ip-service-key")
    public Map<String, Object> getIpServiceKey(
            @RequestParam(name = "nskIp") String nskIp) {
        Map<String, Object> result = new HashMap<>();
        List<NdmsServiceKey> ndmsServiceKeys = ServiceKeyHolder.getNdmsServiceKeys();
        result.put("header", HeaderDto.fromErrorCode(ErrorCode.UnknownError));
        for (NdmsServiceKey ndmsServiceKey : ndmsServiceKeys) {
            // ndmsServiceKey의 nskIp가 nskIp와 같은 경우
            if (ndmsServiceKey.getNskIp().equals(nskIp)) {
                result.put("header", HeaderDto.fromErrorCode(ErrorCode.NomalService));
                result.put("body", ndmsServiceKey);
            }
        }

        return result;
    }

    @PutMapping("/service-key")
    @ResponseBody
    public Map<String, Object> putServiceKey(
        NdmsServiceKeyDto ndmsServiceKeyDto
    ) {
        Map<String, Object> result = new HashMap<>();
        result.put("header", HeaderDto.fromErrorCode(ErrorCode.NomalService));
        try {
            boolean isUpdate = serviceKeyService.modifyServiceKey(ndmsServiceKeyDto);
            if (!isUpdate) throw new Exception();
        } catch (DataAccessException e) {
            log.error("[modifyServiceKey] DataAccessException : {}", e.getMessage());
            result.put("header", HeaderDto.fromErrorCode(ErrorCode.DBUpdateError));
        } catch (Exception e) {
            log.error("[modifyServiceKey] Exception : {}", e.getMessage());
            result.put("header", HeaderDto.fromErrorCode(ErrorCode.DBUpdateError));
        }
        return result;
    }

    // POST 요청 "/service-key"에 대한 처리
    @PostMapping("/service-key")
    @ResponseBody
    public Map<String, Object> postServiceKey(NdmsServiceKeyDto ndmsServiceKeyDto) {
        Map<String, Object> result = new HashMap<>();
        result.put("header", HeaderDto.fromErrorCode(ErrorCode.NomalService));
        try {
            NdmsServiceKey ndmsServiceKey = serviceKeyService.createServiceKey(ndmsServiceKeyDto);
            if (ndmsServiceKey == null) throw new Exception();
            result.put("body", ndmsServiceKey);
        } catch (DataAccessException e) {
            log.error("[createServiceKey] DataAccessException : {}", e.getMessage());
            result.put("header", HeaderDto.fromErrorCode(ErrorCode.DBUpdateError));
        } catch (Exception e) {
            log.error("[createServiceKey] Exception : {}", e.getMessage());
            result.put("header", HeaderDto.fromErrorCode(ErrorCode.DBInsertError));
        }

        return result;
    }
}
