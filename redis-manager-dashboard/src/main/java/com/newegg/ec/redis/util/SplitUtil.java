package com.newegg.ec.redis.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 8/7/2019
 */
public class SplitUtil {

    public static final String COMMAS = ",";

    public static final String EQUAL_SIGN = "=";

    public static final String COLON = ":";

    public static final String SEMICOLON = ";";

    public static final String AITE = "@";

    public static final String SPACE = " ";

    private SplitUtil(){}

    public static final String[] splitByCommas(String str) {
        return splitBySign(str, COMMAS);
    }

    public static final String[] splitByEqualSign(String str) {
        return splitBySign(str, EQUAL_SIGN);
    }

    public static final String[] splitByColon(String str) {
        return splitBySign(str, COLON);
    }

    public static final String[] splitBySemicolon(String str) {
        return splitBySign(str, SEMICOLON);
    }

    public static final String[] splitByAite(String str) {
        return splitBySign(str, AITE);
    }

    public static final String[] splitBySpace(String str) {
        return splitBySign(str, SPACE);
    }

    public static final String[] splitBySign(String str, String sign) {
        return str.split(sign);
    }
}