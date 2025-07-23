package com.android.systemui.statusbar.phone.domain.interactor;

import android.graphics.Rect;
import java.util.Collection;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DarkStateWithoutIntensity {
    public final Collection areas;
    public final boolean isDark;

    public DarkStateWithoutIntensity(Collection<Rect> collection, boolean z) {
        this.areas = collection;
        this.isDark = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DarkStateWithoutIntensity)) {
            return false;
        }
        DarkStateWithoutIntensity darkStateWithoutIntensity = (DarkStateWithoutIntensity) obj;
        return Intrinsics.areEqual(this.areas, darkStateWithoutIntensity.areas) && this.isDark == darkStateWithoutIntensity.isDark;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isDark) + (this.areas.hashCode() * 31);
    }

    public final String toString() {
        return "DarkStateWithoutIntensity(areas=" + this.areas + ", isDark=" + this.isDark + ")";
    }
}
