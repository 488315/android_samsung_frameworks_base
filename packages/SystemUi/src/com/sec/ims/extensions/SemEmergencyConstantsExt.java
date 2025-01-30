package com.sec.ims.extensions;

import com.samsung.android.emergencymode.SemEmergencyConstants;
import java.lang.reflect.Field;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SemEmergencyConstantsExt {
    public static final String EMERGENCY_CHECK_ABNORMAL_STATE = getStringFromField("EMERGENCY_CHECK_ABNORMAL_STATE", "com.samsung.intent.action.EMERGENCY_CHECK_ABNORMAL_STATE");

    public static String getStringFromField(String str, String str2) {
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
