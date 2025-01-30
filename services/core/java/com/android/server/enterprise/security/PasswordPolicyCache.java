package com.android.server.enterprise.security;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class PasswordPolicyCache {
    public static final PasswordPolicyCache INSTANCE = new PasswordPolicyCache();
    public final Object mLock = new Object();
    public Map mChangeRequested = new HashMap();

    public static PasswordPolicyCache getInstance() {
        return INSTANCE;
    }

    public void setChangeRequestedAsUser(int i, int i2) {
        synchronized (this.mLock) {
            this.mChangeRequested.put(Integer.valueOf(i), Integer.valueOf(i2));
        }
    }

    public int isChangeRequestedAsUser(int i) {
        int intValue;
        synchronized (this.mLock) {
            intValue = this.mChangeRequested.get(Integer.valueOf(i)) == null ? 0 : ((Integer) this.mChangeRequested.get(Integer.valueOf(i))).intValue();
        }
        return intValue;
    }
}
