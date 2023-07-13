package com.simpledesign.ndms.common;

import com.simpledesign.ndms.servicekey.entity.NdmsServiceKey;
import lombok.Data;

import java.util.List;

@Data
public class ServiceKeyHolder {
    private static List<NdmsServiceKey> ndmsServiceKeys;

    public static List<NdmsServiceKey> getNdmsServiceKeys() {
        return ServiceKeyHolder.ndmsServiceKeys;
    }

    public static void setNdmsServiceKeys(List<NdmsServiceKey> ndmsServiceKeys) {
        ServiceKeyHolder.ndmsServiceKeys = ndmsServiceKeys;
    }
}
