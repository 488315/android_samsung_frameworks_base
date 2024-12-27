package com.android.server.enterprise.security;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
