package com.android.server.enterprise.utils;

import com.android.server.devicepolicy.AbUpdateInstaller$$ExternalSyntheticOutline0;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class NetdHelper {
    public static final String CMD_DELIMITER;
    public static final int CMD_INVALID;
    public static final String CMD_PATH;
    public static final String PARAM_DELIMITER;
    public static final Map allowedCommands;

    static {
        HashMap hashMap = new HashMap();
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(0, hashMap, "ip rule", 1, "ip -6 rule");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(2, hashMap, "ip route", 3, "ip -6 route");
        allowedCommands = Collections.unmodifiableMap(hashMap);
        CMD_INVALID = -1;
        CMD_DELIMITER = ";";
        PARAM_DELIMITER = "\\s+";
        CMD_PATH = "/system/bin/";
    }

    public static Integer getCmdNumber(String str) {
        if (str != null && !str.isEmpty()) {
            String trim = str.replace(CMD_PATH, "").trim();
            for (Map.Entry entry : allowedCommands.entrySet()) {
                if (trim.startsWith((String) entry.getValue())) {
                    return (Integer) entry.getKey();
                }
            }
        }
        return Integer.valueOf(CMD_INVALID);
    }

    public static String[] getCmdParams(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        Integer cmdNumber = getCmdNumber(str);
        if (cmdNumber.intValue() == CMD_INVALID) {
            return null;
        }
        return str.replace(CMD_PATH, "")
                .replace((CharSequence) allowedCommands.get(cmdNumber), "")
                .trim()
                .split(PARAM_DELIMITER);
    }
}
