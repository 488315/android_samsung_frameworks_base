package com.android.systemui.navigationbar.model;

import android.graphics.Point;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public int gestureDisablePolicy;
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
    public boolean userSetupCompleteForCurrentUser;

    public NavBarStates(Point point, boolean z, boolean z2, LayoutProvider layoutProvider, int i, int i2, int i3, boolean z3, int i4, boolean z4, boolean z5, int i5, boolean z6, int i6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, int i7, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, int i8, Boolean bool) {
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
        this.userSetupCompleteForCurrentUser = z5;
        this.iconHint = i5;
        this.sPayShowing = z6;
        this.gestureDisablePolicy = i6;
        this.recentVisible = z7;
        this.homeVisible = z8;
        this.backVisible = z9;
        this.hardKeyIntentPolicy = z10;
        this.imeDownButtonForAllRotation = z11;
        this.transitionMode = i7;
        this.regionSamplingEnabled = z12;
        this.displayChanged = z13;
        this.layoutChangedBeforeAttached = z14;
        this.supportCoverScreen = z15;
        this.supportLargeCoverScreen = z16;
        this.lastTaskUserId = i8;
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
        boolean z5 = navBarStates.userSetupCompleteForCurrentUser;
        int i5 = navBarStates.iconHint;
        boolean z6 = navBarStates.sPayShowing;
        int i6 = navBarStates.gestureDisablePolicy;
        boolean z7 = navBarStates.recentVisible;
        boolean z8 = navBarStates.homeVisible;
        boolean z9 = navBarStates.backVisible;
        boolean z10 = navBarStates.hardKeyIntentPolicy;
        boolean z11 = navBarStates.imeDownButtonForAllRotation;
        int i7 = navBarStates.transitionMode;
        boolean z12 = navBarStates.regionSamplingEnabled;
        boolean z13 = navBarStates.displayChanged;
        boolean z14 = navBarStates.layoutChangedBeforeAttached;
        boolean z15 = navBarStates.supportCoverScreen;
        boolean z16 = navBarStates.supportLargeCoverScreen;
        int i8 = navBarStates.lastTaskUserId;
        Boolean bool = navBarStates.multiModalForLargeCover;
        navBarStates.getClass();
        return new NavBarStates(point, z, z2, layoutProvider, i, i2, i3, z3, i4, z4, z5, i5, z6, i6, z7, z8, z9, z10, z11, i7, z12, z13, z14, z15, z16, i8, bool);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NavBarStates)) {
            return false;
        }
        NavBarStates navBarStates = (NavBarStates) obj;
        return Intrinsics.areEqual(this.displaySize, navBarStates.displaySize) && this.canMove == navBarStates.canMove && this.supportPhoneLayoutProvider == navBarStates.supportPhoneLayoutProvider && Intrinsics.areEqual(this.layoutProvider, navBarStates.layoutProvider) && this.navigationMode == navBarStates.navigationMode && this.disable1 == navBarStates.disable1 && this.disable2 == navBarStates.disable2 && this.darkMode == navBarStates.darkMode && this.rotation == navBarStates.rotation && this.deviceProvisioned == navBarStates.deviceProvisioned && this.userSetupCompleteForCurrentUser == navBarStates.userSetupCompleteForCurrentUser && this.iconHint == navBarStates.iconHint && this.sPayShowing == navBarStates.sPayShowing && this.gestureDisablePolicy == navBarStates.gestureDisablePolicy && this.recentVisible == navBarStates.recentVisible && this.homeVisible == navBarStates.homeVisible && this.backVisible == navBarStates.backVisible && this.hardKeyIntentPolicy == navBarStates.hardKeyIntentPolicy && this.imeDownButtonForAllRotation == navBarStates.imeDownButtonForAllRotation && this.transitionMode == navBarStates.transitionMode && this.regionSamplingEnabled == navBarStates.regionSamplingEnabled && this.displayChanged == navBarStates.displayChanged && this.layoutChangedBeforeAttached == navBarStates.layoutChangedBeforeAttached && this.supportCoverScreen == navBarStates.supportCoverScreen && this.supportLargeCoverScreen == navBarStates.supportLargeCoverScreen && this.lastTaskUserId == navBarStates.lastTaskUserId && Intrinsics.areEqual(this.multiModalForLargeCover, navBarStates.multiModalForLargeCover);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.displaySize.hashCode() * 31, 31, this.canMove), 31, this.supportPhoneLayoutProvider);
        LayoutProvider layoutProvider = this.layoutProvider;
        int m2 = ReorderTile$$ExternalSyntheticOutline0.m(this.lastTaskUserId, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.transitionMode, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.gestureDisablePolicy, TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.iconHint, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.rotation, TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.disable2, ReorderTile$$ExternalSyntheticOutline0.m(this.disable1, ReorderTile$$ExternalSyntheticOutline0.m(this.navigationMode, (m + (layoutProvider == null ? 0 : layoutProvider.hashCode())) * 31, 31), 31), 31), 31, this.darkMode), 31), 31, this.deviceProvisioned), 31, this.userSetupCompleteForCurrentUser), 31), 31, this.sPayShowing), 31), 31, this.recentVisible), 31, this.homeVisible), 31, this.backVisible), 31, this.hardKeyIntentPolicy), 31, this.imeDownButtonForAllRotation), 31), 31, this.regionSamplingEnabled), 31, this.displayChanged), 31, this.layoutChangedBeforeAttached), 31, this.supportCoverScreen), 31, this.supportLargeCoverScreen), 31);
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
        boolean z5 = this.userSetupCompleteForCurrentUser;
        int i5 = this.iconHint;
        boolean z6 = this.sPayShowing;
        int i6 = this.gestureDisablePolicy;
        boolean z7 = this.recentVisible;
        boolean z8 = this.homeVisible;
        boolean z9 = this.backVisible;
        boolean z10 = this.hardKeyIntentPolicy;
        boolean z11 = this.imeDownButtonForAllRotation;
        int i7 = this.transitionMode;
        boolean z12 = this.regionSamplingEnabled;
        boolean z13 = this.displayChanged;
        boolean z14 = this.layoutChangedBeforeAttached;
        boolean z15 = this.supportCoverScreen;
        boolean z16 = this.supportLargeCoverScreen;
        int i8 = this.lastTaskUserId;
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
        ViewPager$$ExternalSyntheticOutline0.m(sb, i, ", disable1=", i2, ", disable2=");
        sb.append(i3);
        sb.append(", darkMode=");
        sb.append(z3);
        sb.append(", rotation=");
        sb.append(i4);
        sb.append(", deviceProvisioned=");
        sb.append(z4);
        sb.append(", userSetupCompleteForCurrentUser=");
        sb.append(z5);
        sb.append(", iconHint=");
        sb.append(i5);
        sb.append(", sPayShowing=");
        sb.append(z6);
        sb.append(", gestureDisablePolicy=");
        sb.append(i6);
        sb.append(", recentVisible=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z7, ", homeVisible=", z8, ", backVisible=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z9, ", hardKeyIntentPolicy=", z10, ", imeDownButtonForAllRotation=");
        sb.append(z11);
        sb.append(", transitionMode=");
        sb.append(i7);
        sb.append(", regionSamplingEnabled=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z12, ", displayChanged=", z13, ", layoutChangedBeforeAttached=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z14, ", supportCoverScreen=", z15, ", supportLargeCoverScreen=");
        sb.append(z16);
        sb.append(", lastTaskUserId=");
        sb.append(i8);
        sb.append(", multiModalForLargeCover=");
        sb.append(bool);
        sb.append(")");
        return sb.toString();
    }

    public /* synthetic */ NavBarStates(Point point, boolean z, boolean z2, LayoutProvider layoutProvider, int i, int i2, int i3, boolean z3, int i4, boolean z4, boolean z5, int i5, boolean z6, int i6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, int i7, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, int i8, Boolean bool, int i9, DefaultConstructorMarker defaultConstructorMarker) {
        this(point, (i9 & 2) != 0 ? true : z, (i9 & 4) != 0 ? true : z2, (i9 & 8) != 0 ? null : layoutProvider, (i9 & 16) != 0 ? 0 : i, (i9 & 32) != 0 ? 0 : i2, (i9 & 64) != 0 ? 0 : i3, (i9 & 128) != 0 ? false : z3, (i9 & 256) != 0 ? 0 : i4, (i9 & 512) != 0 ? false : z4, (i9 & 1024) != 0 ? false : z5, (i9 & 2048) != 0 ? 0 : i5, (i9 & 4096) != 0 ? false : z6, (i9 & 8192) != 0 ? 0 : i6, (i9 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? true : z7, (i9 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? true : z8, (i9 & 65536) != 0 ? true : z9, (i9 & 131072) != 0 ? false : z10, (i9 & 262144) != 0 ? false : z11, (i9 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? 0 : i7, (i9 & 1048576) != 0 ? false : z12, (i9 & 2097152) != 0 ? false : z13, (i9 & 4194304) != 0 ? false : z14, (i9 & 8388608) != 0 ? false : z15, (i9 & 16777216) != 0 ? false : z16, (i9 & 33554432) == 0 ? i8 : 0, (i9 & 67108864) != 0 ? null : bool);
    }
}
