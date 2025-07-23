package com.samsung.vekit.Manager;

import android.util.Log;
import com.samsung.vekit.Common.Object.LogProfile;
import com.samsung.vekit.Common.Type.ManagerType;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class LogProfileManager extends Manager<LogProfile> {
    public LogProfileManager(VEContext vEContext) {
        super(vEContext, ManagerType.LOG_PROFILE);
        this.TAG = getClass().getSimpleName();
    }

    public LogProfile create(String str, String str2) {
        try {
            LogProfile logProfile = new LogProfile(this.context, generateUniqueId(), str, str2);
            add(logProfile);
            return logProfile;
        } catch (Exception e) {
            Log.e(this.TAG, "create: ", e);
            return null;
        }
    }

    public LogProfile create(String str, String str2, String str3, String str4) {
        try {
            LogProfile logProfile = new LogProfile(this.context, generateUniqueId(), str, str2, str3, str4);
            add(logProfile);
            return logProfile;
        } catch (Exception e) {
            Log.e(this.TAG, "create: ", e);
            return null;
        }
    }
}
