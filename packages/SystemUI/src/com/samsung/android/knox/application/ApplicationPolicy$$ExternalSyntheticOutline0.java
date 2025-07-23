package com.samsung.android.knox.application;

import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract /* synthetic */ class ApplicationPolicy$$ExternalSyntheticOutline0 {
    public static IApplicationPolicy m(ContextInfo contextInfo, String str, ApplicationPolicy applicationPolicy, String str2) {
        AccessController.throwIfParentInstance(contextInfo, str);
        applicationPolicy.logUsage(str2);
        return applicationPolicy.getService();
    }
}
