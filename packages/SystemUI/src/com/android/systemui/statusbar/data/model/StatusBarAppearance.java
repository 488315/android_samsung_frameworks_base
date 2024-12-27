package com.android.systemui.statusbar.data.model;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.statusbar.phone.BoundsPair;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

public final class StatusBarAppearance {
    public final List appearanceRegions;
    public final BoundsPair bounds;
    public final StatusBarMode mode;
    public final boolean navbarColorManagedByIme;

    public StatusBarAppearance(StatusBarMode statusBarMode, BoundsPair boundsPair, List<? extends AppearanceRegion> list, boolean z) {
        this.mode = statusBarMode;
        this.bounds = boundsPair;
        this.appearanceRegions = list;
        this.navbarColorManagedByIme = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StatusBarAppearance)) {
            return false;
        }
        StatusBarAppearance statusBarAppearance = (StatusBarAppearance) obj;
        return this.mode == statusBarAppearance.mode && Intrinsics.areEqual(this.bounds, statusBarAppearance.bounds) && Intrinsics.areEqual(this.appearanceRegions, statusBarAppearance.appearanceRegions) && this.navbarColorManagedByIme == statusBarAppearance.navbarColorManagedByIme;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.navbarColorManagedByIme) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.appearanceRegions, (this.bounds.hashCode() + (this.mode.hashCode() * 31)) * 31, 31);
    }

    public final String toString() {
        return "StatusBarAppearance(mode=" + this.mode + ", bounds=" + this.bounds + ", appearanceRegions=" + this.appearanceRegions + ", navbarColorManagedByIme=" + this.navbarColorManagedByIme + ")";
    }
}
