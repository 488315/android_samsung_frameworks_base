package com.android.wm.shell.shared.bubbles;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Rect;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class DeviceConfig {
    public static final Companion Companion = new Companion(null);
    public final DisplayCutout displayCutout;
    public final Insets insets;
    public final boolean isLandscape;
    public final boolean isLargeScreen;
    public final boolean isRtl;
    public final boolean isSmallTablet;
    public final Rect windowBounds;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public DeviceConfig(boolean z, boolean z2, boolean z3, boolean z4, Rect rect, Insets insets, DisplayCutout displayCutout) {
        this.isLargeScreen = z;
        this.isSmallTablet = z2;
        this.isLandscape = z3;
        this.isRtl = z4;
        this.windowBounds = rect;
        this.insets = insets;
        this.displayCutout = displayCutout;
    }

    public static final DeviceConfig create(Context context, WindowManager windowManager, DisplayCutout displayCutout) {
        Companion.getClass();
        WindowMetrics currentWindowMetrics = windowManager.getCurrentWindowMetrics();
        Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.statusBars() | WindowInsets.Type.displayCutout());
        Rect bounds = currentWindowMetrics.getBounds();
        context.getResources().getConfiguration();
        boolean z = context.getResources().getConfiguration().orientation == 2;
        boolean z2 = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context) == 1;
        context.getResources().getConfiguration();
        return new DeviceConfig(false, false, z, z2, bounds, insetsIgnoringVisibility, displayCutout);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeviceConfig)) {
            return false;
        }
        DeviceConfig deviceConfig = (DeviceConfig) obj;
        return this.isLargeScreen == deviceConfig.isLargeScreen && this.isSmallTablet == deviceConfig.isSmallTablet && this.isLandscape == deviceConfig.isLandscape && this.isRtl == deviceConfig.isRtl && Intrinsics.areEqual(this.windowBounds, deviceConfig.windowBounds) && Intrinsics.areEqual(this.insets, deviceConfig.insets) && Intrinsics.areEqual(this.displayCutout, deviceConfig.displayCutout);
    }

    public final int hashCode() {
        int iHashCode = (this.insets.hashCode() + ((this.windowBounds.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.isLargeScreen) * 31, 31, this.isSmallTablet), 31, this.isLandscape), 31, this.isRtl)) * 31)) * 31;
        DisplayCutout displayCutout = this.displayCutout;
        return iHashCode + (displayCutout == null ? 0 : displayCutout.hashCode());
    }

    public final String toString() {
        return "DeviceConfig(isLargeScreen=" + this.isLargeScreen + ", isSmallTablet=" + this.isSmallTablet + ", isLandscape=" + this.isLandscape + ", isRtl=" + this.isRtl + ", windowBounds=" + this.windowBounds + ", insets=" + this.insets + ", displayCutout=" + this.displayCutout + ")";
    }
}
