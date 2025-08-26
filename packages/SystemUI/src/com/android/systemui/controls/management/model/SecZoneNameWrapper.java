package com.android.systemui.controls.management.model;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
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
