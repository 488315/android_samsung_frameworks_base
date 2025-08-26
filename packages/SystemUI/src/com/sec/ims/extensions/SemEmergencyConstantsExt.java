package com.sec.ims.extensions;

import com.samsung.android.emergencymode.SemEmergencyConstants;
import java.lang.reflect.Field;

/* loaded from: classes4.dex */
public class SemEmergencyConstantsExt {
    public static final String EMERGENCY_CHECK_ABNORMAL_STATE = getStringFromField("EMERGENCY_CHECK_ABNORMAL_STATE", "com.samsung.intent.action.EMERGENCY_CHECK_ABNORMAL_STATE");

    public static String getStringFromField(String str, String str2) throws NoSuchFieldException {
        try {
            Field field = ReflectionUtils.getField(SemEmergencyConstants.class, str);
            if (field != null) {
                return (String) field.get(null);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return str2;
    }
}
