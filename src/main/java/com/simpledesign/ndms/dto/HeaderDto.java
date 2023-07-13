package com.simpledesign.ndms.dto;

import com.simpledesign.ndms.common.ErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HeaderDto {
    private String resultMsg;
    private String resultCode;
    private String errorMsg;

    public HeaderDto(String resultCode, String errorMsg, String description) {
        this.resultMsg = description;
        this.resultCode = resultCode;
        this.errorMsg = errorMsg;
    }

    // Getter and Setter methods
    //...

    // Factory method to create ErrorDto object from ErrorCode enum
    public static HeaderDto fromErrorCode(ErrorCode errorCode) {
        return new HeaderDto(errorCode.getCode(), errorCode.getMessage(), errorCode.getDescription());
    }
}

