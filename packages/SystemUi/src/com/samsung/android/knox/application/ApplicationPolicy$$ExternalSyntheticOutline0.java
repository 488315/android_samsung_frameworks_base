package com.samsung.android.knox.application;

import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract /* synthetic */ class ApplicationPolicy$$ExternalSyntheticOutline0 {
    /* renamed from: m */
    public static IApplicationPolicy m242m(ContextInfo contextInfo, String str, ApplicationPolicy applicationPolicy, String str2) {
        AccessController.throwIfParentInstance(contextInfo, str);
        applicationPolicy.logUsage(str2);
        return applicationPolicy.getService();
    }
}
