package com.android.systemui.controls.controller.util;

import androidx.appcompat.view.menu.SeslMenuItem;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
