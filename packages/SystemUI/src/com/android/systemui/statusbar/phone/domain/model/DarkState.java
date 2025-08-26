package com.android.systemui.statusbar.phone.domain.model;

import android.graphics.Rect;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Collection;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class DarkState {
    public final Collection areas;
    public final float darkIntensity;
    public final int tint;

    public DarkState(Collection<Rect> collection, int i, float f) {
        this.areas = collection;
        this.tint = i;
        this.darkIntensity = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DarkState)) {
            return false;
        }
        DarkState darkState = (DarkState) obj;
        return Intrinsics.areEqual(this.areas, darkState.areas) && this.tint == darkState.tint && Float.compare(this.darkIntensity, darkState.darkIntensity) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.darkIntensity) + ReorderTile$$ExternalSyntheticOutline0.m(this.tint, this.areas.hashCode() * 31, 31);
    }

    public final String toString() {
        Collection collection = this.areas;
        StringBuilder sb = new StringBuilder("DarkState(areas=");
        sb.append(collection);
        sb.append(", tint=");
        sb.append(this.tint);
        sb.append(", darkIntensity=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(this.darkIntensity, ")", sb);
    }
}
