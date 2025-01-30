package com.android.systemui.controls.controller.util;

import androidx.appcompat.view.menu.SeslMenuItem;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
