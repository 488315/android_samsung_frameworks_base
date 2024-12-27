package com.android.server.enterprise.security;

import android.content.ComponentName;

import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
