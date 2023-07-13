package com.simpledesign.ndms.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class NdmsFirstInitializer implements CommandLineRunner {
    @Autowired
    IsDefaultInfoSetPath defaultInfoSetPath;
    public static final String fileName = "is_ndms_default_info_set.txt";
    public static int isDefaultInfoSet = 0;

    public static final List<Object> firstClassList = Arrays.asList(
    );

    @Override
    public void run(String... args) throws Exception {
        try {
            Path filePath = Paths.get(defaultInfoSetPath.getPath() + "/" + fileName);

            if (!Files.exists(filePath)) {
                Files.write(filePath, String.valueOf(isDefaultInfoSet).getBytes());
            } else {
                String isInfoSetString = new String(Files.readAllBytes(filePath));
                if (!isInfoSetString.equals("0") && !isInfoSetString.equals("1"))
                    Files.write(filePath, String.valueOf(isDefaultInfoSet).getBytes());

                isDefaultInfoSet = Integer.parseInt(isInfoSetString);
            }
        }
        catch (IOException e) {
            log.error("[{}] Error reading file: {}", getClass().getSimpleName(), e.getMessage());
        }
    }
}
