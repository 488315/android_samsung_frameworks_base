package com.samsung.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import com.android.telephony.Rlog;

import java.util.HashMap;
import java.util.Locale;

/* loaded from: classes6.dex */
public class SemNetworkQualityInfo {
    private static final String FLOAT_TYPE = "3";
    private static final String INTEGER_TYPE = "1";
    private static final String LOG_TAG = "SemNetworkQualityInfo";
    private static final String LONG_TYPE = "4";
    private static final String STRING_TYPE = "2";
    private HashMap<String, Object> map = new HashMap<>();
    private HashMap<String, String> typeMap = new HashMap<>();

    public int getIntValue(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        String keyLowercase = key.toLowerCase(Locale.ROOT);
        String type = this.typeMap.get(keyLowercase);
        if ("1".equals(type)) {
            return ((Integer) this.map.get(keyLowercase)).intValue();
        }
        String msg =
                "getIntValue Wrong Type of key [" + key + NavigationBarInflaterView.SIZE_MOD_END;
        throw new IllegalArgumentException(msg);
    }

    public float getFloatValue(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        String keyLowercase = key.toLowerCase(Locale.ROOT);
        String type = this.typeMap.get(keyLowercase);
        if (FLOAT_TYPE.equals(type)) {
            return ((Float) this.map.get(keyLowercase)).floatValue();
        }
        String msg =
                "getFloatValue Wrong Type of key [" + key + NavigationBarInflaterView.SIZE_MOD_END;
        throw new IllegalArgumentException(msg);
    }

    public String getStringValue(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        String keyLowercase = key.toLowerCase(Locale.ROOT);
        String type = this.typeMap.get(keyLowercase);
        if (STRING_TYPE.equals(type)) {
            return (String) this.map.get(keyLowercase);
        }
        String msg =
                "getStringValue Wrong Type of key [" + key + NavigationBarInflaterView.SIZE_MOD_END;
        throw new IllegalArgumentException(msg);
    }

    public long getLongValue(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        String keyLowercase = key.toLowerCase(Locale.ROOT);
        String type = this.typeMap.get(keyLowercase);
        if (LONG_TYPE.equals(type)) {
            return ((Long) this.map.get(keyLowercase)).longValue();
        }
        String msg =
                "getLongValue Wrong Type of key [" + key + NavigationBarInflaterView.SIZE_MOD_END;
        throw new IllegalArgumentException(msg);
    }

    public void put(String key, String val, String type_t) {
        if (key != null) {
            String keyLowercase = key.toLowerCase(Locale.ROOT);
            if ("--".equals(val)) {
                this.map.put(keyLowercase, null);
                this.typeMap.put(keyLowercase, STRING_TYPE);
                return;
            }
            if ("1".equals(type_t)) {
                this.map.put(keyLowercase, Integer.valueOf(Integer.parseInt(val)));
                this.typeMap.put(keyLowercase, "1");
                return;
            }
            if (STRING_TYPE.equals(type_t)) {
                this.map.put(keyLowercase, val);
                this.typeMap.put(keyLowercase, STRING_TYPE);
            } else if (FLOAT_TYPE.equals(type_t)) {
                this.map.put(keyLowercase, Float.valueOf(Float.parseFloat(val)));
                this.typeMap.put(keyLowercase, FLOAT_TYPE);
            } else if (LONG_TYPE.equals(type_t)) {
                this.map.put(keyLowercase, Long.valueOf(Long.parseLong(val)));
                this.typeMap.put(keyLowercase, LONG_TYPE);
            } else {
                Rlog.d(
                        LOG_TAG,
                        "getMobileQualityInfo Wrong Type["
                                + type_t
                                + NavigationBarInflaterView.SIZE_MOD_END);
            }
        }
    }
}
