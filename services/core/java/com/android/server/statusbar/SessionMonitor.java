package com.android.server.statusbar;

import android.app.StatusBarManager;
import android.content.Context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SessionMonitor {
    public final Context mContext;
    public final Map mSessionToListeners = new HashMap();

    public SessionMonitor(Context context) {
        this.mContext = context;
        for (Integer num : StatusBarManager.ALL_SESSIONS) {
            num.intValue();
            this.mSessionToListeners.put(num, new HashSet());
        }
    }

    public final void requireSetterPermissions(int i) {
        if ((i & 1) != 0) {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.CONTROL_KEYGUARD",
                    "StatusBarManagerService.SessionMonitor");
        }
        if ((i & 2) != 0) {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.STATUS_BAR_SERVICE",
                    "StatusBarManagerService.SessionMonitor");
        }
    }
}
