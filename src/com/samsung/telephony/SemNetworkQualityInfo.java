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

    public int getIntValue(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        if ("1".equals(this.typeMap.get(lowerCase))) {
            return ((Integer) this.map.get(lowerCase)).intValue();
        }
        throw new IllegalArgumentException("getIntValue Wrong Type of key [" + str + NavigationBarInflaterView.SIZE_MOD_END);
    }

    public float getFloatValue(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        if (FLOAT_TYPE.equals(this.typeMap.get(lowerCase))) {
            return ((Float) this.map.get(lowerCase)).floatValue();
        }
        throw new IllegalArgumentException("getFloatValue Wrong Type of key [" + str + NavigationBarInflaterView.SIZE_MOD_END);
    }

    public String getStringValue(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        if (STRING_TYPE.equals(this.typeMap.get(lowerCase))) {
            return (String) this.map.get(lowerCase);
        }
        throw new IllegalArgumentException("getStringValue Wrong Type of key [" + str + NavigationBarInflaterView.SIZE_MOD_END);
    }

    public long getLongValue(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        if (LONG_TYPE.equals(this.typeMap.get(lowerCase))) {
            return ((Long) this.map.get(lowerCase)).longValue();
        }
        throw new IllegalArgumentException("getLongValue Wrong Type of key [" + str + NavigationBarInflaterView.SIZE_MOD_END);
    }

    public void put(String str, String str2, String str3) {
        if (str != null) {
            String lowerCase = str.toLowerCase(Locale.ROOT);
            if ("--".equals(str2)) {
                this.map.put(lowerCase, null);
                this.typeMap.put(lowerCase, STRING_TYPE);
                return;
            }
            if ("1".equals(str3)) {
                this.map.put(lowerCase, Integer.valueOf(Integer.parseInt(str2)));
                this.typeMap.put(lowerCase, "1");
                return;
            }
            if (STRING_TYPE.equals(str3)) {
                this.map.put(lowerCase, str2);
                this.typeMap.put(lowerCase, STRING_TYPE);
                return;
            }
            if (FLOAT_TYPE.equals(str3)) {
                this.map.put(lowerCase, Float.valueOf(Float.parseFloat(str2)));
                this.typeMap.put(lowerCase, FLOAT_TYPE);
            } else if (LONG_TYPE.equals(str3)) {
                this.map.put(lowerCase, Long.valueOf(Long.parseLong(str2)));
                this.typeMap.put(lowerCase, LONG_TYPE);
            } else {
                Rlog.d(LOG_TAG, "getMobileQualityInfo Wrong Type[" + str3 + NavigationBarInflaterView.SIZE_MOD_END);
            }
        }
    }
}
