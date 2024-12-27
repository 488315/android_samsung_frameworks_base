package com.android.systemui.controls.controller.util;

import androidx.appcompat.view.menu.SeslMenuItem;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
