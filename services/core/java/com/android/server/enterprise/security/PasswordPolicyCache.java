package com.android.server.enterprise.security;

import java.util.HashMap;
import java.util.Map;

public final class PasswordPolicyCache {
    public static final PasswordPolicyCache INSTANCE = new PasswordPolicyCache();
    public final Object mLock = new Object();
    public final Map mChangeRequested = new HashMap();

    public final void setChangeRequestedAsUser(int i, int i2) {
        synchronized (this.mLock) {
            ((HashMap) this.mChangeRequested).put(Integer.valueOf(i), Integer.valueOf(i2));
        }
    }
}
