package com.android.systemui.activity.data.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AppVisibilityModel {
    public final boolean isAppCurrentlyVisible;
    public final Long lastAppVisibleTime;

    public AppVisibilityModel() {
        this(false, null, 3, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppVisibilityModel)) {
            return false;
        }
        AppVisibilityModel appVisibilityModel = (AppVisibilityModel) obj;
        return this.isAppCurrentlyVisible == appVisibilityModel.isAppCurrentlyVisible && Intrinsics.areEqual(this.lastAppVisibleTime, appVisibilityModel.lastAppVisibleTime);
    }

    public final int hashCode() {
        int hashCode = Boolean.hashCode(this.isAppCurrentlyVisible) * 31;
        Long l = this.lastAppVisibleTime;
        return hashCode + (l == null ? 0 : l.hashCode());
    }

    public final String toString() {
        return "AppVisibilityModel(isAppCurrentlyVisible=" + this.isAppCurrentlyVisible + ", lastAppVisibleTime=" + this.lastAppVisibleTime + ")";
    }

    public AppVisibilityModel(boolean z, Long l) {
        this.isAppCurrentlyVisible = z;
        this.lastAppVisibleTime = l;
    }

    public /* synthetic */ AppVisibilityModel(boolean z, Long l, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? null : l);
    }
}
