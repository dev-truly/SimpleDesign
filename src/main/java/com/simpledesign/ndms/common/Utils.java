package com.simpledesign.ndms.common;

import com.simpledesign.ndms.common.obsr.GbSearchCode;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Utils {
    public final static DateTimeFormatter OBSV_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public final static Set<GbSearchCode> DEFAUILT_GB_SEARCH = Set.of(
            GbSearchCode.raw,
            GbSearchCode.avg1h,
            GbSearchCode.avg1d
    );

    public static Map<String, String> convertEntityToMap(Object entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        Map<String, String> params = new HashMap<>();

        for (Field field : fields) {
            boolean accessible = field.canAccess(entity);
            try {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (value != null) {
                    params.put(field.getName(), value.toString());
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                field.setAccessible(accessible);
            }
        }
        return params;
    }

    public static ErrorCode getErrorCode(String code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        throw new IllegalArgumentException("No enum constant " + ErrorCode.class.getCanonicalName() + " with code " + code);
    }
}
