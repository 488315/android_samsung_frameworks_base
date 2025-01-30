package com.android.systemui.media.controls.p010ui;

import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.settingslib.udfps.UdfpsOverlayParams$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RippleData {
    public float alpha;
    public float highlight;
    public float maxSize;
    public float minSize;
    public float progress;

    /* renamed from: x */
    public float f308x;

    /* renamed from: y */
    public float f309y;

    public RippleData(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        this.f308x = f;
        this.f309y = f2;
        this.alpha = f3;
        this.progress = f4;
        this.minSize = f5;
        this.maxSize = f6;
        this.highlight = f7;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RippleData)) {
            return false;
        }
        RippleData rippleData = (RippleData) obj;
        return Float.compare(this.f308x, rippleData.f308x) == 0 && Float.compare(this.f309y, rippleData.f309y) == 0 && Float.compare(this.alpha, rippleData.alpha) == 0 && Float.compare(this.progress, rippleData.progress) == 0 && Float.compare(this.minSize, rippleData.minSize) == 0 && Float.compare(this.maxSize, rippleData.maxSize) == 0 && Float.compare(this.highlight, rippleData.highlight) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.highlight) + UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.maxSize, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.minSize, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.progress, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.alpha, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.f309y, Float.hashCode(this.f308x) * 31, 31), 31), 31), 31), 31);
    }

    public final String toString() {
        float f = this.f308x;
        float f2 = this.f309y;
        float f3 = this.alpha;
        float f4 = this.progress;
        float f5 = this.minSize;
        float f6 = this.maxSize;
        float f7 = this.highlight;
        StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("RippleData(x=", f, ", y=", f2, ", alpha=");
        m88m.append(f3);
        m88m.append(", progress=");
        m88m.append(f4);
        m88m.append(", minSize=");
        m88m.append(f5);
        m88m.append(", maxSize=");
        m88m.append(f6);
        m88m.append(", highlight=");
        m88m.append(f7);
        m88m.append(")");
        return m88m.toString();
    }
}
