package com.android.systemui.controls.controller.util;

import androidx.appcompat.view.menu.SeslMenuItem;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class BadgeObserver {
    public final SeslMenuItem menuItem;

    public BadgeObserver(SeslMenuItem seslMenuItem) {
        this.menuItem = seslMenuItem;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof BadgeObserver) && Intrinsics.areEqual(this.menuItem, ((BadgeObserver) obj).menuItem);
    }

    public final int hashCode() {
        return this.menuItem.hashCode();
    }

    public final String toString() {
        return "BadgeObserver(menuItem=" + this.menuItem + ")";
    }
}
