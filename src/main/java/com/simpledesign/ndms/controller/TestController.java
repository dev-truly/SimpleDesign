package com.simpledesign.ndms.controller;

import com.simpledesign.ndms.common.ErrorCode;
import com.simpledesign.ndms.common.IsDefaultInfoSetPath;
import com.simpledesign.ndms.common.NdmsFirstInitializer;
import com.simpledesign.ndms.dto.HeaderDto;
import com.simpledesign.ndms.dto.SetDto;
import com.simpledesign.ndms.repository.DefaultInfoRepository;
import com.simpledesign.ndms.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class TestController {
    @Autowired
    AuthService authService;

    @Autowired
    IsDefaultInfoSetPath defaultInfoSetPath;


    /*@ResponseBody
    @GetMapping("/test")
    public String test(HttpServletRequest req) {
        return authService.getAuthKey(req);
    }*/

    @GetMapping("/test2")
    public ModelAndView test2(ModelAndView mv) {
        mv.setViewName("views/approve");
        //System.out.println(cdDistObsv);
        return mv;
    }

    @GetMapping("/test")
    @ResponseBody
    public SetDto test(String dsCode) {
        SetDto setDto = new SetDto();
        setDto.setHeader(HeaderDto.fromErrorCode(ErrorCode.NomalService));
        setDto.setBody(dsCode);

        log.info(dsCode);

        return setDto;
    }

    @Autowired
    DefaultInfoRepository defaultInfoRepository;

    @GetMapping("/test3")
    @ResponseBody
    @Transactional
    public SetDto test3() {
        Map<String, Integer> result = new HashMap<>();
        SetDto setDto = new SetDto();
        setDto.setHeader(HeaderDto.fromErrorCode(ErrorCode.NomalService));
        try {
            int tcmCouDngrDsriskCnt = defaultInfoRepository.updateTcmCouDngrDsrisk();
            int tcmCouObsvCnt = defaultInfoRepository.updateTcmCouObsv();
            int tcmCouTholdCnt = defaultInfoRepository.updateTcmCouThold();
            int tcmCouCctvInfoCnt = defaultInfoRepository.updateTcmCouCctvInfo();

            result.put("TcmCouDngrDsrisk", tcmCouDngrDsriskCnt);
            result.put("TcmCouObsv", tcmCouObsvCnt);
            result.put("TcmCouThold", tcmCouTholdCnt);
            result.put("TcmCouCctvInfo", tcmCouCctvInfoCnt);

            Path filePath = Paths.get(defaultInfoSetPath.getPath() + "/" + NdmsFirstInitializer.fileName);

            Files.write(filePath, String.valueOf(1).getBytes());
            NdmsFirstInitializer.isDefaultInfoSet = 1;

            setDto.setBody(result);
        } catch (Exception e) {
            log.info("{}", e.getMessage());
        }

        return setDto;
    }
}
