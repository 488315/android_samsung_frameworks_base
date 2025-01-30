package com.samsung.android.jdsms;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Binder;

/* loaded from: classes5.dex */
final class PolicyEnforcer {
    private static final String SUBTAG = "[POLICY] ";
    private static final UidAllowList mUidAllowList = new UidAllowList();
    private static final CallerVerifier mCallerVerifier = new CallerVerifier();

    PolicyEnforcer() {
    }

    static final boolean isAValidCaller() {
        if (!mCallerVerifier.wasCallerValid()) {
            DsmsLog.m280e("[POLICY] Unauthorized Caller");
            return false;
        }
        return true;
    }

    static final boolean isAValidUser(Context context) {
        int uid = Binder.getCallingUid();
        String callingUid = context.getPackageManager().getNameForUid(uid);
        if (callingUid == null) {
            if (!mUidAllowList.containsUid("OEM_UID:" + Integer.toString(uid))) {
                DsmsLog.m280e("Unauthorized OEM_UID [" + uid + "] name [" + callingUid + NavigationBarInflaterView.SIZE_MOD_END);
                return false;
            }
        } else if (!mUidAllowList.containsUid(callingUid)) {
            DsmsLog.m280e("[POLICY] Unauthorized uid [" + uid + "] name [" + callingUid + NavigationBarInflaterView.SIZE_MOD_END);
            return false;
        }
        DsmsLog.m278d("[POLICY] ALLOW uid [" + uid + "] name [" + callingUid + NavigationBarInflaterView.SIZE_MOD_END);
        return true;
    }
}
