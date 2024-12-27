package com.android.systemui.navigationbar.model;

import android.graphics.Point;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NavBarStates {
    public boolean backVisible;
    public boolean canMove;
    public boolean darkMode;
    public boolean deviceProvisioned;
    public int disable1;
    public int disable2;
    public boolean displayChanged;
    public Point displaySize;
    public boolean gestureDisabledByPolicy;
    public boolean hardKeyIntentPolicy;
    public boolean homeVisible;
    public int iconHint;
    public boolean imeDownButtonForAllRotation;
    public int lastTaskUserId;
    public boolean layoutChangedBeforeAttached;
    public LayoutProvider layoutProvider;
    public Boolean multiModalForLargeCover;
    public int navigationMode;
    public boolean recentVisible;
    public boolean regionSamplingEnabled;
    public int rotation;
    public boolean sPayShowing;
    public boolean supportCoverScreen;
    public boolean supportLargeCoverScreen;
    public boolean supportPhoneLayoutProvider;
    public int transitionMode;
    public boolean userSetupCompleted;

    public NavBarStates(Point point, boolean z, boolean z2, LayoutProvider layoutProvider, int i, int i2, int i3, boolean z3, int i4, boolean z4, boolean z5, int i5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, int i6, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, int i7, Boolean bool) {
        this.displaySize = point;
        this.canMove = z;
        this.supportPhoneLayoutProvider = z2;
        this.layoutProvider = layoutProvider;
        this.navigationMode = i;
        this.disable1 = i2;
        this.disable2 = i3;
        this.darkMode = z3;
        this.rotation = i4;
        this.deviceProvisioned = z4;
        this.userSetupCompleted = z5;
        this.iconHint = i5;
        this.sPayShowing = z6;
        this.gestureDisabledByPolicy = z7;
        this.recentVisible = z8;
        this.homeVisible = z9;
        this.backVisible = z10;
        this.hardKeyIntentPolicy = z11;
        this.imeDownButtonForAllRotation = z12;
        this.transitionMode = i6;
        this.regionSamplingEnabled = z13;
        this.displayChanged = z14;
        this.layoutChangedBeforeAttached = z15;
        this.supportCoverScreen = z16;
        this.supportLargeCoverScreen = z17;
        this.lastTaskUserId = i7;
        this.multiModalForLargeCover = bool;
    }

    public static NavBarStates copy$default(NavBarStates navBarStates) {
        Point point = navBarStates.displaySize;
        boolean z = navBarStates.canMove;
        boolean z2 = navBarStates.supportPhoneLayoutProvider;
        LayoutProvider layoutProvider = navBarStates.layoutProvider;
        int i = navBarStates.navigationMode;
        int i2 = navBarStates.disable1;
        int i3 = navBarStates.disable2;
        boolean z3 = navBarStates.darkMode;
        int i4 = navBarStates.rotation;
        boolean z4 = navBarStates.deviceProvisioned;
        boolean z5 = navBarStates.userSetupCompleted;
        int i5 = navBarStates.iconHint;
        boolean z6 = navBarStates.sPayShowing;
        boolean z7 = navBarStates.gestureDisabledByPolicy;
        boolean z8 = navBarStates.recentVisible;
        boolean z9 = navBarStates.homeVisible;
        boolean z10 = navBarStates.backVisible;
        boolean z11 = navBarStates.hardKeyIntentPolicy;
        boolean z12 = navBarStates.imeDownButtonForAllRotation;
        int i6 = navBarStates.transitionMode;
        boolean z13 = navBarStates.regionSamplingEnabled;
        boolean z14 = navBarStates.displayChanged;
        boolean z15 = navBarStates.layoutChangedBeforeAttached;
        boolean z16 = navBarStates.supportCoverScreen;
        boolean z17 = navBarStates.supportLargeCoverScreen;
        int i7 = navBarStates.lastTaskUserId;
        Boolean bool = navBarStates.multiModalForLargeCover;
        navBarStates.getClass();
        return new NavBarStates(point, z, z2, layoutProvider, i, i2, i3, z3, i4, z4, z5, i5, z6, z7, z8, z9, z10, z11, z12, i6, z13, z14, z15, z16, z17, i7, bool);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NavBarStates)) {
            return false;
        }
        NavBarStates navBarStates = (NavBarStates) obj;
        return Intrinsics.areEqual(this.displaySize, navBarStates.displaySize) && this.canMove == navBarStates.canMove && this.supportPhoneLayoutProvider == navBarStates.supportPhoneLayoutProvider && Intrinsics.areEqual(this.layoutProvider, navBarStates.layoutProvider) && this.navigationMode == navBarStates.navigationMode && this.disable1 == navBarStates.disable1 && this.disable2 == navBarStates.disable2 && this.darkMode == navBarStates.darkMode && this.rotation == navBarStates.rotation && this.deviceProvisioned == navBarStates.deviceProvisioned && this.userSetupCompleted == navBarStates.userSetupCompleted && this.iconHint == navBarStates.iconHint && this.sPayShowing == navBarStates.sPayShowing && this.gestureDisabledByPolicy == navBarStates.gestureDisabledByPolicy && this.recentVisible == navBarStates.recentVisible && this.homeVisible == navBarStates.homeVisible && this.backVisible == navBarStates.backVisible && this.hardKeyIntentPolicy == navBarStates.hardKeyIntentPolicy && this.imeDownButtonForAllRotation == navBarStates.imeDownButtonForAllRotation && this.transitionMode == navBarStates.transitionMode && this.regionSamplingEnabled == navBarStates.regionSamplingEnabled && this.displayChanged == navBarStates.displayChanged && this.layoutChangedBeforeAttached == navBarStates.layoutChangedBeforeAttached && this.supportCoverScreen == navBarStates.supportCoverScreen && this.supportLargeCoverScreen == navBarStates.supportLargeCoverScreen && this.lastTaskUserId == navBarStates.lastTaskUserId && Intrinsics.areEqual(this.multiModalForLargeCover, navBarStates.multiModalForLargeCover);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.displaySize.hashCode() * 31, 31, this.canMove), 31, this.supportPhoneLayoutProvider);
        LayoutProvider layoutProvider = this.layoutProvider;
        int m2 = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.lastTaskUserId, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.transitionMode, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.iconHint, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.rotation, TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.disable2, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.disable1, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.navigationMode, (m + (layoutProvider == null ? 0 : layoutProvider.hashCode())) * 31, 31), 31), 31), 31, this.darkMode), 31), 31, this.deviceProvisioned), 31, this.userSetupCompleted), 31), 31, this.sPayShowing), 31, this.gestureDisabledByPolicy), 31, this.recentVisible), 31, this.homeVisible), 31, this.backVisible), 31, this.hardKeyIntentPolicy), 31, this.imeDownButtonForAllRotation), 31), 31, this.regionSamplingEnabled), 31, this.displayChanged), 31, this.layoutChangedBeforeAttached), 31, this.supportCoverScreen), 31, this.supportLargeCoverScreen), 31);
        Boolean bool = this.multiModalForLargeCover;
        return m2 + (bool != null ? bool.hashCode() : 0);
    }

    public final String toString() {
        Point point = this.displaySize;
        boolean z = this.canMove;
        boolean z2 = this.supportPhoneLayoutProvider;
        LayoutProvider layoutProvider = this.layoutProvider;
        int i = this.navigationMode;
        int i2 = this.disable1;
        int i3 = this.disable2;
        boolean z3 = this.darkMode;
        int i4 = this.rotation;
        boolean z4 = this.deviceProvisioned;
        boolean z5 = this.userSetupCompleted;
        int i5 = this.iconHint;
        boolean z6 = this.sPayShowing;
        boolean z7 = this.gestureDisabledByPolicy;
        boolean z8 = this.recentVisible;
        boolean z9 = this.homeVisible;
        boolean z10 = this.backVisible;
        boolean z11 = this.hardKeyIntentPolicy;
        boolean z12 = this.imeDownButtonForAllRotation;
        int i6 = this.transitionMode;
        boolean z13 = this.regionSamplingEnabled;
        boolean z14 = this.displayChanged;
        boolean z15 = this.layoutChangedBeforeAttached;
        boolean z16 = this.supportCoverScreen;
        boolean z17 = this.supportLargeCoverScreen;
        int i7 = this.lastTaskUserId;
        Boolean bool = this.multiModalForLargeCover;
        StringBuilder sb = new StringBuilder("NavBarStates(displaySize=");
        sb.append(point);
        sb.append(", canMove=");
        sb.append(z);
        sb.append(", supportPhoneLayoutProvider=");
        sb.append(z2);
        sb.append(", layoutProvider=");
        sb.append(layoutProvider);
        sb.append(", navigationMode=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(sb, i, ", disable1=", i2, ", disable2=");
        sb.append(i3);
        sb.append(", darkMode=");
        sb.append(z3);
        sb.append(", rotation=");
        sb.append(i4);
        sb.append(", deviceProvisioned=");
        sb.append(z4);
        sb.append(", userSetupCompleted=");
        sb.append(z5);
        sb.append(", iconHint=");
        sb.append(i5);
        sb.append(", sPayShowing=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z6, ", gestureDisabledByPolicy=", z7, ", recentVisible=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z8, ", homeVisible=", z9, ", backVisible=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z10, ", hardKeyIntentPolicy=", z11, ", imeDownButtonForAllRotation=");
        sb.append(z12);
        sb.append(", transitionMode=");
        sb.append(i6);
        sb.append(", regionSamplingEnabled=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z13, ", displayChanged=", z14, ", layoutChangedBeforeAttached=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z15, ", supportCoverScreen=", z16, ", supportLargeCoverScreen=");
        sb.append(z17);
        sb.append(", lastTaskUserId=");
        sb.append(i7);
        sb.append(", multiModalForLargeCover=");
        sb.append(bool);
        sb.append(")");
        return sb.toString();
    }

    public /* synthetic */ NavBarStates(Point point, boolean z, boolean z2, LayoutProvider layoutProvider, int i, int i2, int i3, boolean z3, int i4, boolean z4, boolean z5, int i5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, int i6, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, int i7, Boolean bool, int i8, DefaultConstructorMarker defaultConstructorMarker) {
        this(point, (i8 & 2) != 0 ? true : z, (i8 & 4) != 0 ? true : z2, (i8 & 8) != 0 ? null : layoutProvider, (i8 & 16) != 0 ? 0 : i, (i8 & 32) != 0 ? 0 : i2, (i8 & 64) != 0 ? 0 : i3, (i8 & 128) != 0 ? false : z3, (i8 & 256) != 0 ? 0 : i4, (i8 & 512) != 0 ? false : z4, (i8 & 1024) != 0 ? true : z5, (i8 & 2048) != 0 ? 0 : i5, (i8 & 4096) != 0 ? false : z6, (i8 & 8192) != 0 ? false : z7, (i8 & 16384) != 0 ? true : z8, (i8 & 32768) != 0 ? true : z9, (i8 & 65536) != 0 ? true : z10, (i8 & 131072) != 0 ? false : z11, (i8 & 262144) != 0 ? false : z12, (i8 & 524288) != 0 ? 0 : i6, (i8 & QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) != 0 ? false : z13, (i8 & QuickStepContract.SYSUI_STATE_DEVICE_DOZING) != 0 ? false : z14, (i8 & QuickStepContract.SYSUI_STATE_BACK_DISABLED) != 0 ? false : z15, (i8 & QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED) != 0 ? false : z16, (i8 & 16777216) != 0 ? false : z17, (i8 & QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING) == 0 ? i7 : 0, (i8 & QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY) != 0 ? null : bool);
    }
}
