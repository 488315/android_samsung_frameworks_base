package com.android.systemui.privacy;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PrivacyApplication {
    public final String packageName;
    public final int uid;

    public PrivacyApplication(String str, int i) {
        this.packageName = str;
        this.uid = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PrivacyApplication)) {
            return false;
        }
        PrivacyApplication privacyApplication = (PrivacyApplication) obj;
        return Intrinsics.areEqual(this.packageName, privacyApplication.packageName) && this.uid == privacyApplication.uid;
    }

    public final int hashCode() {
        return Integer.hashCode(this.uid) + (this.packageName.hashCode() * 31);
    }

    public final String toString() {
        return "PrivacyApplication(packageName=" + this.packageName + ", uid=" + this.uid + ")";
    }
}
