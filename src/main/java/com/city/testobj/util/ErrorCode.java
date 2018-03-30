package com.city.testobj.util;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ErrorCode extends Serializable {
    Logger LOGGER = LoggerFactory.getLogger(ErrorCode.class);
    Map<Integer, ErrorCode> ALL_ERROR_CODES = new TreeMap<>();


    /**
     * 操作成功
     */
    ErrorCode SUCCESS = new BaseErrorCode(0, "OK");

    static <T extends ErrorCode> T valueOf(Integer code) {
        T value = (T) ALL_ERROR_CODES.get(code);
        if (value == null) {
            LOGGER.warn("return null,code {}", code);
        }
        return value;
    }

    /**
     * 获取全局错误码
     *
     * @return
     */
    int getErrorCode();

    /**
     * 获取错误信息描述
     *
     * @return
     */
    String getErrorMsg();

}
