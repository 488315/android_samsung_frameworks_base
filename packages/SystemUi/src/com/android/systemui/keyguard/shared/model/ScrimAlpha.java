package com.android.systemui.keyguard.shared.model;

import com.android.settingslib.udfps.UdfpsOverlayParams$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ScrimAlpha {
    public final float behindAlpha;
    public final float frontAlpha;
    public final float notificationsAlpha;

    public ScrimAlpha() {
        this(0.0f, 0.0f, 0.0f, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ScrimAlpha)) {
            return false;
        }
        ScrimAlpha scrimAlpha = (ScrimAlpha) obj;
        return Float.compare(this.frontAlpha, scrimAlpha.frontAlpha) == 0 && Float.compare(this.behindAlpha, scrimAlpha.behindAlpha) == 0 && Float.compare(this.notificationsAlpha, scrimAlpha.notificationsAlpha) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.notificationsAlpha) + UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.behindAlpha, Float.hashCode(this.frontAlpha) * 31, 31);
    }

    public final String toString() {
        return "ScrimAlpha(frontAlpha=" + this.frontAlpha + ", behindAlpha=" + this.behindAlpha + ", notificationsAlpha=" + this.notificationsAlpha + ")";
    }

    public ScrimAlpha(float f, float f2, float f3) {
        this.frontAlpha = f;
        this.behindAlpha = f2;
        this.notificationsAlpha = f3;
    }

    public /* synthetic */ ScrimAlpha(float f, float f2, float f3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2, (i & 4) != 0 ? 0.0f : f3);
    }
}
