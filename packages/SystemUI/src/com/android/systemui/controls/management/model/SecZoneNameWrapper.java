package com.android.systemui.controls.management.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
