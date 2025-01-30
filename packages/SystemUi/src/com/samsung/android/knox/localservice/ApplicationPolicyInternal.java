package com.samsung.android.knox.localservice;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ApplicationPolicyInternal {
    public abstract String getApplicationNameFromDb(String str, int i);

    public abstract boolean getApplicationStateEnabledAsUser(String str, boolean z, int i);

    public abstract boolean isApplicationStartDisabledAsUser(String str, int i);

    public abstract boolean isApplicationStopDisabledAsUser(String str, int i, String str2, String str3, String str4, boolean z);
}
