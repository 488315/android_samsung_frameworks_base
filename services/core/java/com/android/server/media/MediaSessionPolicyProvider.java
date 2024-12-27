package com.android.server.media;

public abstract class MediaSessionPolicyProvider {
    static final int SESSION_POLICY_IGNORE_BUTTON_RECEIVER = 1;
    static final int SESSION_POLICY_IGNORE_BUTTON_SESSION = 2;

    public abstract int getSessionPoliciesForApplication(int i, String str);
}
