package com.android.server.enterprise.security;

import android.content.ComponentName;

import com.samsung.android.knox.ContextInfo;

public abstract /* synthetic */ class PasswordPolicy$$ExternalSyntheticOutline0 {
    public static ContextInfo m(
            PasswordPolicy passwordPolicy,
            ContextInfo contextInfo,
            ComponentName componentName,
            ContextInfo contextInfo2) {
        passwordPolicy.getEDM$28().enforceComponentCheck(contextInfo, componentName);
        return passwordPolicy.enforceSecurityPermission$1(contextInfo2);
    }
}
