package com.samsung.android.knox;

import android.os.Binder;
import android.os.UserHandle;
import android.util.Log;
import com.sec.ims.configuration.DATA;

/* loaded from: classes4.dex */
public class EdmUtils {
    public static String TAG = "EnterpriseDeviceManager";
    public static final int UNEXPECTED_ERROR = 0;

    public static int getAPILevelForInternal() {
        try {
            return Integer.parseInt(DATA.DM_FIELD_INDEX.TIMER_VZW);
        } catch (NumberFormatException unused) {
            Log.w(TAG, "Failed parsing API level");
            return 0;
        }
    }

    public static int getCallingUserId(ContextInfo contextInfo) {
        if (contextInfo == null) {
            contextInfo = new ContextInfo(Binder.getCallingUid());
        }
        return SemPersonaManager.isKnoxId(contextInfo.mContainerId) ? contextInfo.mContainerId : UserHandle.getUserId(contextInfo.mCallerUid);
    }
}
