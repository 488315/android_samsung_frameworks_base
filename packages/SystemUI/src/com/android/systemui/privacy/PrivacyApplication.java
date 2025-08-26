package com.android.systemui.privacy;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

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
        StringBuilder sb = new StringBuilder("PrivacyApplication(packageName=");
        sb.append(this.packageName);
        sb.append(", uid=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.uid, ")", sb);
    }
}
