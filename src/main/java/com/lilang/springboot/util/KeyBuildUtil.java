package com.lilang.springboot.util;

import com.google.common.base.Joiner;

/**
 * ${DESCRIPTION}
 * User: lilang
 * Date: 2017/9/7 ProjectName: springboot-test Version：5.0.0
 */
public class KeyBuildUtil {

    /**
     * 拼接符
     */
    private static final String SPLICE = ":";

    public static String buildKey(String ...params) {
        String key = Joiner.on(SPLICE).skipNulls().join(params);
        return key;
    }

}
