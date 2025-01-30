package com.android.systemui.controls.management;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ZoneNameWrapper extends ElementWrapper {
    public final CharSequence zoneName;

    public ZoneNameWrapper(CharSequence charSequence) {
        super(null);
        this.zoneName = charSequence;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ZoneNameWrapper) && Intrinsics.areEqual(this.zoneName, ((ZoneNameWrapper) obj).zoneName);
    }

    public final int hashCode() {
        return this.zoneName.hashCode();
    }

    public final String toString() {
        return "ZoneNameWrapper(zoneName=" + ((Object) this.zoneName) + ")";
    }
}
