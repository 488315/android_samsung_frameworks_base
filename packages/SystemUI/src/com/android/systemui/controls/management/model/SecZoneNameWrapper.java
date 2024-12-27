package com.android.systemui.controls.management.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SecZoneNameWrapper extends SecElementWrapper {
    public final CharSequence zoneName;

    public SecZoneNameWrapper(CharSequence charSequence) {
        super(null);
        this.zoneName = charSequence;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SecZoneNameWrapper) && Intrinsics.areEqual(this.zoneName, ((SecZoneNameWrapper) obj).zoneName);
    }

    public final int hashCode() {
        return this.zoneName.hashCode();
    }

    public final String toString() {
        return "SecZoneNameWrapper(zoneName=" + ((Object) this.zoneName) + ")";
    }
}
