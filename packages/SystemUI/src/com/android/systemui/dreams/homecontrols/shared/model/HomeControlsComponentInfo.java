package com.android.systemui.dreams.homecontrols.shared.model;

import android.content.ComponentName;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class HomeControlsComponentInfo {
    public final boolean allowTrivialControlsOnLockscreen;
    public final ComponentName componentName;

    public HomeControlsComponentInfo(ComponentName componentName, boolean z) {
        this.componentName = componentName;
        this.allowTrivialControlsOnLockscreen = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HomeControlsComponentInfo)) {
            return false;
        }
        HomeControlsComponentInfo homeControlsComponentInfo = (HomeControlsComponentInfo) obj;
        return Intrinsics.areEqual(this.componentName, homeControlsComponentInfo.componentName) && this.allowTrivialControlsOnLockscreen == homeControlsComponentInfo.allowTrivialControlsOnLockscreen;
    }

    public final int hashCode() {
        ComponentName componentName = this.componentName;
        return Boolean.hashCode(this.allowTrivialControlsOnLockscreen) + ((componentName == null ? 0 : componentName.hashCode()) * 31);
    }

    public final String toString() {
        return "HomeControlsComponentInfo(componentName=" + this.componentName + ", allowTrivialControlsOnLockscreen=" + this.allowTrivialControlsOnLockscreen + ")";
    }
}
