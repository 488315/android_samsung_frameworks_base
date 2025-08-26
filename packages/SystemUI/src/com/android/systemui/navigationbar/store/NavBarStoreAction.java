package com.android.systemui.navigationbar.store;

import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import com.android.systemui.shared.navigationbar.NavBarEvents;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface NavBarStoreAction {

    public final class Action {
        public final boolean a11yClickable;
        public final boolean a11yLongClickable;
        public final boolean contextualButtonVisible;
        public final float dampingRatio;
        public final float darkIntensity;
        public final boolean disableSUWBack;
        public final int displayId;
        public final int edgeBackGestureDisablePolicy;
        public final boolean folded;
        public final GestureHintVIInfo gestureHintVIInfo;
        public final LinearLayout leftRemoteViewContainer;
        public final int navBarIconHints;
        public final NavBarLayoutInfo navBarLayoutInfo;
        public final int navBarVisibility;
        public final OneHandModeInfo oneHandModeInfo;
        public final float remoteViewDarkIntensity;
        public final RemoteViewShortcut remoteViewShortcut;
        public final LinearLayout rightRemoteViewContainer;
        public final int rotation;
        public final float stiffness;
        public final List sysUiFlagInfoList;
        public final boolean taskbarEnabled;
        public final NavBarEvents taskbarNavBarEvents;

        public Action() {
            this(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Action)) {
                return false;
            }
            Action action = (Action) obj;
            return Intrinsics.areEqual(this.oneHandModeInfo, action.oneHandModeInfo) && Intrinsics.areEqual(this.navBarLayoutInfo, action.navBarLayoutInfo) && Intrinsics.areEqual(this.leftRemoteViewContainer, action.leftRemoteViewContainer) && Intrinsics.areEqual(this.rightRemoteViewContainer, action.rightRemoteViewContainer) && this.contextualButtonVisible == action.contextualButtonVisible && Float.compare(this.remoteViewDarkIntensity, action.remoteViewDarkIntensity) == 0 && Intrinsics.areEqual(this.remoteViewShortcut, action.remoteViewShortcut) && this.disableSUWBack == action.disableSUWBack && Float.compare(this.darkIntensity, action.darkIntensity) == 0 && this.navBarIconHints == action.navBarIconHints && this.a11yClickable == action.a11yClickable && this.a11yLongClickable == action.a11yLongClickable && this.navBarVisibility == action.navBarVisibility && this.edgeBackGestureDisablePolicy == action.edgeBackGestureDisablePolicy && Intrinsics.areEqual(this.gestureHintVIInfo, action.gestureHintVIInfo) && Intrinsics.areEqual(this.sysUiFlagInfoList, action.sysUiFlagInfoList) && this.folded == action.folded && this.taskbarEnabled == action.taskbarEnabled && Intrinsics.areEqual(this.taskbarNavBarEvents, action.taskbarNavBarEvents) && Float.compare(this.stiffness, action.stiffness) == 0 && Float.compare(this.dampingRatio, action.dampingRatio) == 0 && this.displayId == action.displayId && this.rotation == action.rotation;
        }

        public final int hashCode() {
            int iHashCode = (this.navBarLayoutInfo.hashCode() + (this.oneHandModeInfo.hashCode() * 31)) * 31;
            LinearLayout linearLayout = this.leftRemoteViewContainer;
            int iHashCode2 = (iHashCode + (linearLayout == null ? 0 : linearLayout.hashCode())) * 31;
            LinearLayout linearLayout2 = this.rightRemoteViewContainer;
            return Integer.hashCode(this.rotation) + ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.dampingRatio, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.stiffness, (this.taskbarNavBarEvents.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.sysUiFlagInfoList, (this.gestureHintVIInfo.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.edgeBackGestureDisablePolicy, ReorderTile$$ExternalSyntheticOutline0.m(this.navBarVisibility, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.navBarIconHints, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.darkIntensity, TransitionData$$ExternalSyntheticOutline0.m((this.remoteViewShortcut.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.remoteViewDarkIntensity, TransitionData$$ExternalSyntheticOutline0.m((iHashCode2 + (linearLayout2 != null ? linearLayout2.hashCode() : 0)) * 31, 31, this.contextualButtonVisible), 31)) * 31, 31, this.disableSUWBack), 31), 31), 31, this.a11yClickable), 31, this.a11yLongClickable), 31), 31)) * 31, 31), 31, this.folded), 31, this.taskbarEnabled)) * 31, 31), 31), 31);
        }

        public final String toString() {
            LinearLayout linearLayout = this.leftRemoteViewContainer;
            LinearLayout linearLayout2 = this.rightRemoteViewContainer;
            List list = this.sysUiFlagInfoList;
            StringBuilder sb = new StringBuilder("Action(oneHandModeInfo=");
            sb.append(this.oneHandModeInfo);
            sb.append(", navBarLayoutInfo=");
            sb.append(this.navBarLayoutInfo);
            sb.append(", leftRemoteViewContainer=");
            sb.append(linearLayout);
            sb.append(", rightRemoteViewContainer=");
            sb.append(linearLayout2);
            sb.append(", contextualButtonVisible=");
            sb.append(this.contextualButtonVisible);
            sb.append(", remoteViewDarkIntensity=");
            sb.append(this.remoteViewDarkIntensity);
            sb.append(", remoteViewShortcut=");
            sb.append(this.remoteViewShortcut);
            sb.append(", disableSUWBack=");
            sb.append(this.disableSUWBack);
            sb.append(", darkIntensity=");
            sb.append(this.darkIntensity);
            sb.append(", navBarIconHints=");
            sb.append(this.navBarIconHints);
            sb.append(", a11yClickable=");
            sb.append(this.a11yClickable);
            sb.append(", a11yLongClickable=");
            sb.append(this.a11yLongClickable);
            sb.append(", navBarVisibility=");
            sb.append(this.navBarVisibility);
            sb.append(", edgeBackGestureDisablePolicy=");
            sb.append(this.edgeBackGestureDisablePolicy);
            sb.append(", gestureHintVIInfo=");
            sb.append(this.gestureHintVIInfo);
            sb.append(", sysUiFlagInfoList=");
            sb.append(list);
            sb.append(", folded=");
            sb.append(this.folded);
            sb.append(", taskbarEnabled=");
            sb.append(this.taskbarEnabled);
            sb.append(", taskbarNavBarEvents=");
            sb.append(this.taskbarNavBarEvents);
            sb.append(", stiffness=");
            sb.append(this.stiffness);
            sb.append(", dampingRatio=");
            sb.append(this.dampingRatio);
            sb.append(", displayId=");
            sb.append(this.displayId);
            sb.append(", rotation=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.rotation, ")", sb);
        }

        public Action(OneHandModeInfo oneHandModeInfo, NavBarLayoutInfo navBarLayoutInfo, LinearLayout linearLayout, LinearLayout linearLayout2, boolean z, float f, RemoteViewShortcut remoteViewShortcut, boolean z2, float f2, int i, boolean z3, boolean z4, int i2, int i3, GestureHintVIInfo gestureHintVIInfo, List<SysUiFlagInfo> list, boolean z5, boolean z6, NavBarEvents navBarEvents, float f3, float f4, int i4, int i5) {
            this.oneHandModeInfo = oneHandModeInfo;
            this.navBarLayoutInfo = navBarLayoutInfo;
            this.leftRemoteViewContainer = linearLayout;
            this.rightRemoteViewContainer = linearLayout2;
            this.contextualButtonVisible = z;
            this.remoteViewDarkIntensity = f;
            this.remoteViewShortcut = remoteViewShortcut;
            this.disableSUWBack = z2;
            this.darkIntensity = f2;
            this.navBarIconHints = i;
            this.a11yClickable = z3;
            this.a11yLongClickable = z4;
            this.navBarVisibility = i2;
            this.edgeBackGestureDisablePolicy = i3;
            this.gestureHintVIInfo = gestureHintVIInfo;
            this.sysUiFlagInfoList = list;
            this.folded = z5;
            this.taskbarEnabled = z6;
            this.taskbarNavBarEvents = navBarEvents;
            this.stiffness = f3;
            this.dampingRatio = f4;
            this.displayId = i4;
            this.rotation = i5;
        }

        public /* synthetic */ Action(OneHandModeInfo oneHandModeInfo, NavBarLayoutInfo navBarLayoutInfo, LinearLayout linearLayout, LinearLayout linearLayout2, boolean z, float f, RemoteViewShortcut remoteViewShortcut, boolean z2, float f2, int i, boolean z3, boolean z4, int i2, int i3, GestureHintVIInfo gestureHintVIInfo, List list, boolean z5, boolean z6, NavBarEvents navBarEvents, float f3, float f4, int i4, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
            this((i6 & 1) != 0 ? new OneHandModeInfo(0, 0, 0.0f, 7, null) : oneHandModeInfo, (i6 & 2) != 0 ? new NavBarLayoutInfo(0, 0, 0, 0, 0, 31, null) : navBarLayoutInfo, (i6 & 4) != 0 ? null : linearLayout, (i6 & 8) == 0 ? linearLayout2 : null, (i6 & 16) != 0 ? false : z, (i6 & 32) != 0 ? 1.0f : f, (i6 & 64) != 0 ? new RemoteViewShortcut(null, null, 0, 0, 15, null) : remoteViewShortcut, (i6 & 128) != 0 ? false : z2, (i6 & 256) == 0 ? f2 : 1.0f, (i6 & 512) != 0 ? 0 : i, (i6 & 1024) != 0 ? false : z3, (i6 & 2048) != 0 ? false : z4, (i6 & 4096) != 0 ? 0 : i2, (i6 & 8192) != 0 ? 0 : i3, (i6 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? new GestureHintVIInfo(0, 0, 0, 0L, 15, null) : gestureHintVIInfo, (i6 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? new ArrayList() : list, (i6 & 65536) != 0 ? false : z5, (i6 & 131072) != 0 ? false : z6, (i6 & 262144) != 0 ? new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 0, 8191, null) : navBarEvents, (i6 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? 0.0f : f3, (i6 & 1048576) == 0 ? f4 : 0.0f, (i6 & 2097152) != 0 ? 0 : i4, (i6 & 4194304) != 0 ? 0 : i5);
        }
    }

    public final class ForceHideGestureHint implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public ForceHideGestureHint() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ForceHideGestureHint) && Intrinsics.areEqual(this.action, ((ForceHideGestureHint) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("ForceHideGestureHint(action="), this.action, ")");
        }

        public ForceHideGestureHint(Action action) {
            this.action = action;
        }

        public /* synthetic */ ForceHideGestureHint(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class GestureHintVIInfo {
        public final int distanceX;
        public final int distanceY;
        public final long duration;
        public final int hintID;

        public GestureHintVIInfo() {
            this(0, 0, 0, 0L, 15, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof GestureHintVIInfo)) {
                return false;
            }
            GestureHintVIInfo gestureHintVIInfo = (GestureHintVIInfo) obj;
            return this.hintID == gestureHintVIInfo.hintID && this.distanceX == gestureHintVIInfo.distanceX && this.distanceY == gestureHintVIInfo.distanceY && this.duration == gestureHintVIInfo.duration;
        }

        public final int hashCode() {
            return Long.hashCode(this.duration) + ReorderTile$$ExternalSyntheticOutline0.m(this.distanceY, ReorderTile$$ExternalSyntheticOutline0.m(this.distanceX, Integer.hashCode(this.hintID) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("GestureHintVIInfo(hintID=");
            sb.append(this.hintID);
            sb.append(", distanceX=");
            sb.append(this.distanceX);
            sb.append(", distanceY=");
            sb.append(this.distanceY);
            sb.append(", duration=");
            return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.duration, ")", sb);
        }

        public GestureHintVIInfo(int i, int i2, int i3, long j) {
            this.hintID = i;
            this.distanceX = i2;
            this.distanceY = i3;
            this.duration = j;
        }

        public /* synthetic */ GestureHintVIInfo(int i, int i2, int i3, long j, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this((i4 & 1) != 0 ? 0 : i, (i4 & 2) != 0 ? 0 : i2, (i4 & 4) != 0 ? 0 : i3, (i4 & 8) != 0 ? 0L : j);
        }
    }

    public final class InvalidateRemoteView implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public InvalidateRemoteView() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof InvalidateRemoteView) && Intrinsics.areEqual(this.action, ((InvalidateRemoteView) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("InvalidateRemoteView(action="), this.action, ")");
        }

        public InvalidateRemoteView(Action action) {
            this.action = action;
        }

        public /* synthetic */ InvalidateRemoteView(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class MoveHintVI implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public MoveHintVI() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof MoveHintVI) && Intrinsics.areEqual(this.action, ((MoveHintVI) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("MoveHintVI(action="), this.action, ")");
        }

        public MoveHintVI(Action action) {
            this.action = action;
        }

        public /* synthetic */ MoveHintVI(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class NavBarIconMarquee implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public NavBarIconMarquee() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof NavBarIconMarquee) && Intrinsics.areEqual(this.action, ((NavBarIconMarquee) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("NavBarIconMarquee(action="), this.action, ")");
        }

        public NavBarIconMarquee(Action action) {
            this.action = action;
        }

        public /* synthetic */ NavBarIconMarquee(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class NavBarLayoutInfo {
        public final int gravity;
        public final int height;
        public final int insetHeight;
        public final int insetWidth;
        public final int width;

        public NavBarLayoutInfo() {
            this(0, 0, 0, 0, 0, 31, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NavBarLayoutInfo)) {
                return false;
            }
            NavBarLayoutInfo navBarLayoutInfo = (NavBarLayoutInfo) obj;
            return this.width == navBarLayoutInfo.width && this.height == navBarLayoutInfo.height && this.insetHeight == navBarLayoutInfo.insetHeight && this.insetWidth == navBarLayoutInfo.insetWidth && this.gravity == navBarLayoutInfo.gravity;
        }

        public final int hashCode() {
            return Integer.hashCode(this.gravity) + ReorderTile$$ExternalSyntheticOutline0.m(this.insetWidth, ReorderTile$$ExternalSyntheticOutline0.m(this.insetHeight, ReorderTile$$ExternalSyntheticOutline0.m(this.height, Integer.hashCode(this.width) * 31, 31), 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("NavBarLayoutInfo(width=");
            sb.append(this.width);
            sb.append(", height=");
            sb.append(this.height);
            sb.append(", insetHeight=");
            sb.append(this.insetHeight);
            sb.append(", insetWidth=");
            sb.append(this.insetWidth);
            sb.append(", gravity=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.gravity, ")", sb);
        }

        public NavBarLayoutInfo(int i, int i2, int i3, int i4, int i5) {
            this.width = i;
            this.height = i2;
            this.insetHeight = i3;
            this.insetWidth = i4;
            this.gravity = i5;
        }

        public /* synthetic */ NavBarLayoutInfo(int i, int i2, int i3, int i4, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
            this((i6 & 1) != 0 ? -1 : i, (i6 & 2) != 0 ? -1 : i2, (i6 & 4) != 0 ? -1 : i3, (i6 & 8) != 0 ? -1 : i4, (i6 & 16) != 0 ? 80 : i5);
        }
    }

    public final class OneHandModeInfo {
        public final int offsetX;
        public final int offsetY;
        public final float scale;

        public OneHandModeInfo() {
            this(0, 0, 0.0f, 7, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof OneHandModeInfo)) {
                return false;
            }
            OneHandModeInfo oneHandModeInfo = (OneHandModeInfo) obj;
            return this.offsetX == oneHandModeInfo.offsetX && this.offsetY == oneHandModeInfo.offsetY && Float.compare(this.scale, oneHandModeInfo.scale) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.scale) + ReorderTile$$ExternalSyntheticOutline0.m(this.offsetY, Integer.hashCode(this.offsetX) * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("OneHandModeInfo(offsetX=");
            sb.append(this.offsetX);
            sb.append(", offsetY=");
            sb.append(this.offsetY);
            sb.append(", scale=");
            return DpCornerSize$$ExternalSyntheticOutline0.m(this.scale, ")", sb);
        }

        public OneHandModeInfo(int i, int i2, float f) {
            this.offsetX = i;
            this.offsetY = i2;
            this.scale = f;
        }

        public /* synthetic */ OneHandModeInfo(int i, int i2, float f, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? -1 : i, (i3 & 2) != 0 ? -1 : i2, (i3 & 4) != 0 ? 0.0f : f);
        }
    }

    public final class RecalculateGestureInsetScale implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public RecalculateGestureInsetScale() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RecalculateGestureInsetScale) && Intrinsics.areEqual(this.action, ((RecalculateGestureInsetScale) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("RecalculateGestureInsetScale(action="), this.action, ")");
        }

        public RecalculateGestureInsetScale(Action action) {
            this.action = action;
        }

        public /* synthetic */ RecalculateGestureInsetScale(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class ReevaluateNavBar implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public ReevaluateNavBar() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ReevaluateNavBar) && Intrinsics.areEqual(this.action, ((ReevaluateNavBar) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("ReevaluateNavBar(action="), this.action, ")");
        }

        public ReevaluateNavBar(Action action) {
            this.action = action;
        }

        public /* synthetic */ ReevaluateNavBar(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class ReinflateNavBar implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public ReinflateNavBar() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ReinflateNavBar) && Intrinsics.areEqual(this.action, ((ReinflateNavBar) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("ReinflateNavBar(action="), this.action, ")");
        }

        public ReinflateNavBar(Action action) {
            this.action = action;
        }

        public /* synthetic */ ReinflateNavBar(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class RemoteViewShortcut {
        public final int position;
        public final int priority;
        public final RemoteViews remoteViews;
        public final String requestClass;

        public RemoteViewShortcut() {
            this(null, null, 0, 0, 15, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RemoteViewShortcut)) {
                return false;
            }
            RemoteViewShortcut remoteViewShortcut = (RemoteViewShortcut) obj;
            return Intrinsics.areEqual(this.requestClass, remoteViewShortcut.requestClass) && Intrinsics.areEqual(this.remoteViews, remoteViewShortcut.remoteViews) && this.position == remoteViewShortcut.position && this.priority == remoteViewShortcut.priority;
        }

        public final int hashCode() {
            String str = this.requestClass;
            int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
            RemoteViews remoteViews = this.remoteViews;
            return Integer.hashCode(this.priority) + ReorderTile$$ExternalSyntheticOutline0.m(this.position, (iHashCode + (remoteViews != null ? remoteViews.hashCode() : 0)) * 31, 31);
        }

        public final String toString() {
            RemoteViews remoteViews = this.remoteViews;
            StringBuilder sb = new StringBuilder("RemoteViewShortcut(requestClass=");
            sb.append(this.requestClass);
            sb.append(", remoteViews=");
            sb.append(remoteViews);
            sb.append(", position=");
            sb.append(this.position);
            sb.append(", priority=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.priority, ")", sb);
        }

        public RemoteViewShortcut(String str, RemoteViews remoteViews, int i, int i2) {
            this.requestClass = str;
            this.remoteViews = remoteViews;
            this.position = i;
            this.priority = i2;
        }

        public /* synthetic */ RemoteViewShortcut(String str, RemoteViews remoteViews, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? "" : str, (i3 & 2) != 0 ? null : remoteViews, (i3 & 4) != 0 ? -1 : i, (i3 & 8) != 0 ? -1 : i2);
        }
    }

    public final class ResetHintVI implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public ResetHintVI() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ResetHintVI) && Intrinsics.areEqual(this.action, ((ResetHintVI) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("ResetHintVI(action="), this.action, ")");
        }

        public ResetHintVI(Action action) {
            this.action = action;
        }

        public /* synthetic */ ResetHintVI(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class SetGestureHintViewGroup implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public SetGestureHintViewGroup() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof SetGestureHintViewGroup) && Intrinsics.areEqual(this.action, ((SetGestureHintViewGroup) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("SetGestureHintViewGroup(action="), this.action, ")");
        }

        public SetGestureHintViewGroup(Action action) {
            this.action = action;
        }

        public /* synthetic */ SetGestureHintViewGroup(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class SetNavBarVisibility implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public SetNavBarVisibility() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof SetNavBarVisibility) && Intrinsics.areEqual(this.action, ((SetNavBarVisibility) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("SetNavBarVisibility(action="), this.action, ")");
        }

        public SetNavBarVisibility(Action action) {
            this.action = action;
        }

        public /* synthetic */ SetNavBarVisibility(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class ShowA11ySwipeUpTipPopup implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public ShowA11ySwipeUpTipPopup() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ShowA11ySwipeUpTipPopup) && Intrinsics.areEqual(this.action, ((ShowA11ySwipeUpTipPopup) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("ShowA11ySwipeUpTipPopup(action="), this.action, ")");
        }

        public ShowA11ySwipeUpTipPopup(Action action) {
            this.action = action;
        }

        public /* synthetic */ ShowA11ySwipeUpTipPopup(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class StartHintVI implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public StartHintVI() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof StartHintVI) && Intrinsics.areEqual(this.action, ((StartHintVI) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("StartHintVI(action="), this.action, ")");
        }

        public StartHintVI(Action action) {
            this.action = action;
        }

        public /* synthetic */ StartHintVI(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class SysUiFlagInfo {
        public final long flag;
        public final boolean value;

        public SysUiFlagInfo() {
            this(0L, false, 3, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SysUiFlagInfo)) {
                return false;
            }
            SysUiFlagInfo sysUiFlagInfo = (SysUiFlagInfo) obj;
            return this.flag == sysUiFlagInfo.flag && this.value == sysUiFlagInfo.value;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.value) + (Long.hashCode(this.flag) * 31);
        }

        public final String toString() {
            return "SysUiFlagInfo(flag=" + this.flag + ", value=" + this.value + ")";
        }

        public SysUiFlagInfo(long j, boolean z) {
            this.flag = j;
            this.value = z;
        }

        public /* synthetic */ SysUiFlagInfo(long j, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 0L : j, (i & 2) != 0 ? false : z);
        }
    }

    public final class UpdateA11YStatus implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateA11YStatus() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateA11YStatus) && Intrinsics.areEqual(this.action, ((UpdateA11YStatus) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateA11YStatus(action="), this.action, ")");
        }

        public UpdateA11YStatus(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateA11YStatus(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateDefaultNavigationBarStatus implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateDefaultNavigationBarStatus() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateDefaultNavigationBarStatus) && Intrinsics.areEqual(this.action, ((UpdateDefaultNavigationBarStatus) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateDefaultNavigationBarStatus(action="), this.action, ")");
        }

        public UpdateDefaultNavigationBarStatus(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateDefaultNavigationBarStatus(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateEdgeBackGestureDisabledPolicy implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateEdgeBackGestureDisabledPolicy() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateEdgeBackGestureDisabledPolicy) && Intrinsics.areEqual(this.action, ((UpdateEdgeBackGestureDisabledPolicy) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateEdgeBackGestureDisabledPolicy(action="), this.action, ")");
        }

        public UpdateEdgeBackGestureDisabledPolicy(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateEdgeBackGestureDisabledPolicy(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateGestureHintVisibility implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateGestureHintVisibility() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateGestureHintVisibility) && Intrinsics.areEqual(this.action, ((UpdateGestureHintVisibility) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateGestureHintVisibility(action="), this.action, ")");
        }

        public UpdateGestureHintVisibility(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateGestureHintVisibility(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateIndicatorSpringParams implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateIndicatorSpringParams() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateIndicatorSpringParams) && Intrinsics.areEqual(this.action, ((UpdateIndicatorSpringParams) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateIndicatorSpringParams(action="), this.action, ")");
        }

        public UpdateIndicatorSpringParams(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateIndicatorSpringParams(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateNavBarGoneStateFlag implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateNavBarGoneStateFlag() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateNavBarGoneStateFlag) && Intrinsics.areEqual(this.action, ((UpdateNavBarGoneStateFlag) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateNavBarGoneStateFlag(action="), this.action, ")");
        }

        public UpdateNavBarGoneStateFlag(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateNavBarGoneStateFlag(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateNavBarIconAndHints implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateNavBarIconAndHints() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateNavBarIconAndHints) && Intrinsics.areEqual(this.action, ((UpdateNavBarIconAndHints) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateNavBarIconAndHints(action="), this.action, ")");
        }

        public UpdateNavBarIconAndHints(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateNavBarIconAndHints(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateNavBarLayoutParams implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateNavBarLayoutParams() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateNavBarLayoutParams) && Intrinsics.areEqual(this.action, ((UpdateNavBarLayoutParams) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateNavBarLayoutParams(action="), this.action, ")");
        }

        public UpdateNavBarLayoutParams(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateNavBarLayoutParams(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateNavBarNormalStyle implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateNavBarNormalStyle() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateNavBarNormalStyle) && Intrinsics.areEqual(this.action, ((UpdateNavBarNormalStyle) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateNavBarNormalStyle(action="), this.action, ")");
        }

        public UpdateNavBarNormalStyle(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateNavBarNormalStyle(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateNavBarOpaqueColor implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateNavBarOpaqueColor() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateNavBarOpaqueColor) && Intrinsics.areEqual(this.action, ((UpdateNavBarOpaqueColor) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateNavBarOpaqueColor(action="), this.action, ")");
        }

        public UpdateNavBarOpaqueColor(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateNavBarOpaqueColor(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateNavBarSUWStyle implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateNavBarSUWStyle() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateNavBarSUWStyle) && Intrinsics.areEqual(this.action, ((UpdateNavBarSUWStyle) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateNavBarSUWStyle(action="), this.action, ")");
        }

        public UpdateNavBarSUWStyle(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateNavBarSUWStyle(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateNavigationIcon implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateNavigationIcon() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateNavigationIcon) && Intrinsics.areEqual(this.action, ((UpdateNavigationIcon) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateNavigationIcon(action="), this.action, ")");
        }

        public UpdateNavigationIcon(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateNavigationIcon(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateOneHandModeInfo implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateOneHandModeInfo() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateOneHandModeInfo) && Intrinsics.areEqual(this.action, ((UpdateOneHandModeInfo) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateOneHandModeInfo(action="), this.action, ")");
        }

        public UpdateOneHandModeInfo(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateOneHandModeInfo(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateRegionSamplingRect implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateRegionSamplingRect() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateRegionSamplingRect) && Intrinsics.areEqual(this.action, ((UpdateRegionSamplingRect) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateRegionSamplingRect(action="), this.action, ")");
        }

        public UpdateRegionSamplingRect(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateRegionSamplingRect(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateRemoteViewContainer implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateRemoteViewContainer() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateRemoteViewContainer) && Intrinsics.areEqual(this.action, ((UpdateRemoteViewContainer) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateRemoteViewContainer(action="), this.action, ")");
        }

        public UpdateRemoteViewContainer(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateRemoteViewContainer(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateRemoteViewDarkIntensity implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateRemoteViewDarkIntensity() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateRemoteViewDarkIntensity) && Intrinsics.areEqual(this.action, ((UpdateRemoteViewDarkIntensity) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateRemoteViewDarkIntensity(action="), this.action, ")");
        }

        public UpdateRemoteViewDarkIntensity(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateRemoteViewDarkIntensity(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateRemoteViewShortcut implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateRemoteViewShortcut() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateRemoteViewShortcut) && Intrinsics.areEqual(this.action, ((UpdateRemoteViewShortcut) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateRemoteViewShortcut(action="), this.action, ")");
        }

        public UpdateRemoteViewShortcut(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateRemoteViewShortcut(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateSUWA11yIcon implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateSUWA11yIcon() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateSUWA11yIcon) && Intrinsics.areEqual(this.action, ((UpdateSUWA11yIcon) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateSUWA11yIcon(action="), this.action, ")");
        }

        public UpdateSUWA11yIcon(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateSUWA11yIcon(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateSUWDarkIntensity implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateSUWDarkIntensity() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateSUWDarkIntensity) && Intrinsics.areEqual(this.action, ((UpdateSUWDarkIntensity) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateSUWDarkIntensity(action="), this.action, ")");
        }

        public UpdateSUWDarkIntensity(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateSUWDarkIntensity(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateSUWDisabled implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateSUWDisabled() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateSUWDisabled) && Intrinsics.areEqual(this.action, ((UpdateSUWDisabled) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateSUWDisabled(action="), this.action, ")");
        }

        public UpdateSUWDisabled(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateSUWDisabled(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateSUWIconHints implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateSUWIconHints() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateSUWIconHints) && Intrinsics.areEqual(this.action, ((UpdateSUWIconHints) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateSUWIconHints(action="), this.action, ")");
        }

        public UpdateSUWIconHints(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateSUWIconHints(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateSysUiFlags implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateSysUiFlags() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateSysUiFlags) && Intrinsics.areEqual(this.action, ((UpdateSysUiFlags) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateSysUiFlags(action="), this.action, ")");
        }

        public UpdateSysUiFlags(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateSysUiFlags(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateTaskBarIconsAndHints implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateTaskBarIconsAndHints() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateTaskBarIconsAndHints) && Intrinsics.areEqual(this.action, ((UpdateTaskBarIconsAndHints) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateTaskBarIconsAndHints(action="), this.action, ")");
        }

        public UpdateTaskBarIconsAndHints(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateTaskBarIconsAndHints(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateTaskBarNavBarEvents implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateTaskBarNavBarEvents() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateTaskBarNavBarEvents) && Intrinsics.areEqual(this.action, ((UpdateTaskBarNavBarEvents) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateTaskBarNavBarEvents(action="), this.action, ")");
        }

        public UpdateTaskBarNavBarEvents(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateTaskBarNavBarEvents(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }

    public final class UpdateTaskbarStatus implements NavBarStoreAction {
        public final Action action;

        /* JADX WARN: Multi-variable type inference failed */
        public UpdateTaskbarStatus() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UpdateTaskbarStatus) && Intrinsics.areEqual(this.action, ((UpdateTaskbarStatus) obj).action);
        }

        public final int hashCode() {
            return this.action.hashCode();
        }

        public final String toString() {
            return NavBarStoreAction$ForceHideGestureHint$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateTaskbarStatus(action="), this.action, ")");
        }

        public UpdateTaskbarStatus(Action action) {
            this.action = action;
        }

        public /* synthetic */ UpdateTaskbarStatus(Action action, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Action action2;
            if ((i & 1) != 0) {
                action2 = new Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            } else {
                action2 = action;
            }
            this(action2);
        }
    }
}
