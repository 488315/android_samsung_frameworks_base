package com.android.systemui.statusbar.phone.domain.model;

import android.graphics.Rect;
import java.util.Collection;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class DarkState {
    public final Collection areas;
    public final int tint;

    public DarkState(Collection<Rect> collection, int i) {
        this.areas = collection;
        this.tint = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DarkState)) {
            return false;
        }
        DarkState darkState = (DarkState) obj;
        return Intrinsics.areEqual(this.areas, darkState.areas) && this.tint == darkState.tint;
    }

    public final int hashCode() {
        return Integer.hashCode(this.tint) + (this.areas.hashCode() * 31);
    }

    public final String toString() {
        return "DarkState(areas=" + this.areas + ", tint=" + this.tint + ")";
    }
}
