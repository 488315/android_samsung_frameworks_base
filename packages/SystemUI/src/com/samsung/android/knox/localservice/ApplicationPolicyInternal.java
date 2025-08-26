package com.samsung.android.knox.localservice;

/* loaded from: classes4.dex */
public abstract class ApplicationPolicyInternal {
    public abstract String getApplicationNameFromDb(String str, int i);

    public abstract boolean getApplicationStateEnabledAsUser(String str, boolean z, int i);

    public abstract boolean isApplicationStartDisabledAsUser(String str, int i);

    public abstract boolean isApplicationStopDisabledAsUser(String str, int i, String str2, String str3, String str4, boolean z);
}
