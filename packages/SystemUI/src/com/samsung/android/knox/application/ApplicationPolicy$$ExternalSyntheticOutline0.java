package com.samsung.android.knox.application;

import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class ApplicationPolicy$$ExternalSyntheticOutline0 {
    public static IApplicationPolicy m(ContextInfo contextInfo, String str, ApplicationPolicy applicationPolicy, String str2) {
        AccessController.throwIfParentInstance(contextInfo, str);
        applicationPolicy.logUsage(str2);
        return applicationPolicy.getService();
    }
}
