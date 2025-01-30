package com.android.server.media;

import android.content.Context;

/* loaded from: classes2.dex */
public abstract class MediaSessionPolicyProvider {
    static final int SESSION_POLICY_IGNORE_BUTTON_RECEIVER = 1;
    static final int SESSION_POLICY_IGNORE_BUTTON_SESSION = 2;

    public int getSessionPoliciesForApplication(int i, String str) {
        return 0;
    }

    public MediaSessionPolicyProvider(Context context) {
    }
}
