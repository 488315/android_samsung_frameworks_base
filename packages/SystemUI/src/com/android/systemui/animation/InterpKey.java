package com.android.systemui.animation;

import android.graphics.fonts.Font;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class InterpKey {
    public final Font end;
    public final int frame;
    public final Font start;

    public InterpKey(Font font, Font font2, int i) {
        this.start = font;
        this.end = font2;
        this.frame = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InterpKey)) {
            return false;
        }
        InterpKey interpKey = (InterpKey) obj;
        return Intrinsics.areEqual(this.start, interpKey.start) && Intrinsics.areEqual(this.end, interpKey.end) && this.frame == interpKey.frame;
    }

    public final int hashCode() {
        Font font = this.start;
        int iHashCode = (font == null ? 0 : font.hashCode()) * 31;
        Font font2 = this.end;
        return Integer.hashCode(this.frame) + ((iHashCode + (font2 != null ? font2.hashCode() : 0)) * 31);
    }

    public final String toString() {
        Font font = this.start;
        Font font2 = this.end;
        StringBuilder sb = new StringBuilder("InterpKey(start=");
        sb.append(font);
        sb.append(", end=");
        sb.append(font2);
        sb.append(", frame=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.frame, ")", sb);
    }
}
