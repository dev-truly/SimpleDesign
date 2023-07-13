package com.simpledesign.ndms.common;

import com.simpledesign.ndms.servicekey.entity.NdmsServiceKey;
import com.simpledesign.ndms.servicekey.repository.NdmsServiceKeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ServiceKeyInitializer implements CommandLineRunner {
    @Autowired
    NdmsServiceKeyRepository ndmsServiceKeyRepository;

    @Override
    public void run(String... args) throws Exception {
        List<NdmsServiceKey> serviceKeys = ndmsServiceKeyRepository.findByIsDelete("N");
        ServiceKeyHolder.setNdmsServiceKeys(serviceKeys);
    }
}
