package com.samsung.android.jdsms;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;

/* loaded from: classes6.dex */
final class PolicyEnforcer {
    private static final String SUBTAG = "[POLICY] ";
    private static final UidAllowList mUidAllowList = new UidAllowList();
    private static final CallerVerifier mCallerVerifier = new CallerVerifier();

    PolicyEnforcer() {
    }

    static final boolean isAValidCaller() {
        if (mCallerVerifier.wasCallerValid()) {
            return true;
        }
        DsmsLog.e("[POLICY] Unauthorized Caller");
        return false;
    }

    static final boolean isAValidUser(Context context) {
        int callingUid = Binder.getCallingUid();
        String nameForUid = context.getPackageManager().getNameForUid(callingUid);
        if (nameForUid == null) {
            if (!mUidAllowList.containsUid("OEM_UID:" + Integer.toString(callingUid))) {
                DsmsLog.e("Unauthorized OEM_UID [" + callingUid + "] name [" + nameForUid + NavigationBarInflaterView.SIZE_MOD_END);
                return false;
            }
        } else if (!mUidAllowList.containsUid(nameForUid)) {
            DsmsLog.e("[POLICY] Unauthorized uid [" + callingUid + "] name [" + nameForUid + NavigationBarInflaterView.SIZE_MOD_END);
            return false;
        }
        DsmsLog.d("[POLICY] ALLOW uid [" + callingUid + "] name [" + nameForUid + NavigationBarInflaterView.SIZE_MOD_END);
        return true;
    }
}
