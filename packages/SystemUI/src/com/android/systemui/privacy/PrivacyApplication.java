package com.android.systemui.privacy;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
