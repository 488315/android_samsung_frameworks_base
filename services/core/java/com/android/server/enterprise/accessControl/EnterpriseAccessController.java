package com.android.server.enterprise.accessControl;

import android.util.Log;

import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class EnterpriseAccessController {
    public static synchronized ContextInfo enforceCaller(ContextInfo contextInfo, String str) {
        ContextInfo enforceCallerInternal;
        synchronized (EnterpriseAccessController.class) {
            Log.d("EnterpriseAccessController", "enforceCaller(): " + str);
            if (str == null || str.isEmpty()) {
                throw new SecurityException("Invalid API");
            }
            AccessControl accessControl =
                    (AccessControl) AccessControlDefinitions.ACCESS_CONTROL_MAP.get(str);
            Log.d("EnterpriseAccessController", "Retrieved Policy: " + accessControl);
            if (accessControl == null) {
                throw new SecurityException("API not defined");
            }
            AccessControl m493clone = accessControl.m493clone();
            m493clone.mApi = str;
            m493clone.updateCallerInfo(contextInfo);
            enforceCallerInternal = enforceCallerInternal(m493clone);
        }
        return enforceCallerInternal;
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x0116, code lost:

       if (r19 == 0) goto L52;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.android.knox.ContextInfo enforceCallerInternal(
            com.android.server.enterprise.accessControl.AccessControl r22) {
        /*
            Method dump skipped, instructions count: 567
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.accessControl.EnterpriseAccessController.enforceCallerInternal(com.android.server.enterprise.accessControl.AccessControl):com.samsung.android.knox.ContextInfo");
    }
}
