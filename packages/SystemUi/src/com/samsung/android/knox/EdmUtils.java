package com.samsung.android.knox;

import android.os.Binder;
import android.os.UserHandle;
import android.util.Log;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EdmUtils {
    public static String TAG = "EnterpriseDeviceManager";
    public static final int UNEXPECTED_ERROR = 0;

    public static int getAPILevelForInternal() {
        try {
            return Integer.parseInt(DATA.DM_FIELD_INDEX.PUBLISH_TIMER_EXTEND);
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
