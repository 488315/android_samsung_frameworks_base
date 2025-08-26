package com.android.systemui.shade;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class ShadeLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ShadeLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("NPVC mLastFlingWasExpanding set to: ", logMessage.getBool1());
            case 1:
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                boolean bool3 = logMessage.getBool3();
                StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("Setting keyguard status bar visibility to: ", ", isOnAod: ", "oldShadeState: ", bool1, bool2);
                ViewPager$$ExternalSyntheticOutline0.m(sbM, int1, ", newShadeState: ", int2, ",animatingUnlockedShadeToKeyguardBypass: ");
                sbM.append(bool3);
                return sbM.toString();
            case 2:
                return logMessage.getStr1() + "; mPanelClosedOnDown=" + logMessage.getBool1() + "; mExpandedFraction=" + logMessage.getDouble1();
            case 3:
                boolean bool12 = logMessage.getBool1();
                boolean bool22 = logMessage.getBool2();
                boolean bool32 = logMessage.getBool3();
                StringBuilder sbM2 = EmergencyButtonController$$ExternalSyntheticOutline0.m("NPVC not intercepting touch, instantExpanding: ", ", !notificationsDragEnabled: ", ", touchDisabled: ", bool12, bool22);
                sbM2.append(bool32);
                return sbM2.toString();
            case 4:
                return "onQsIntercept: move action, QS tracking enabled. h = " + logMessage.getDouble1();
            case 5:
                boolean bool13 = logMessage.getBool1();
                boolean bool23 = logMessage.getBool2();
                boolean bool33 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                String str1 = logMessage.getStr1();
                StringBuilder sbM3 = EmergencyButtonController$$ExternalSyntheticOutline0.m("CentralSurfaces updateNotificationPanelTouchState set disabled to: ", "\nisGoingToSleep: ", ", !shouldControlScreenOff: ", bool13, bool23);
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM3, bool33, ",!mDeviceInteractive: ", bool4, ", !isPulsing: ");
                sbM3.append(str1);
                return sbM3.toString();
            case 6:
                return "hasVibratedOnOpen=" + logMessage.getBool1() + ", expansionFraction=" + logMessage.getDouble1();
            case 7:
                return logMessage.getStr1() + "; force=" + logMessage.getBool1() + "; expand=" + logMessage.getBool2();
            case 8:
                long long1 = logMessage.getLong1();
                long long2 = logMessage.getLong2();
                int int12 = logMessage.getInt1();
                double double1 = logMessage.getDouble1();
                boolean bool14 = logMessage.getBool1();
                boolean bool24 = logMessage.getBool2();
                StringBuilder sbM4 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("NPVC flingExpands called with vel: ", long1, ", vectorVel: ");
                sbM4.append(long2);
                sbM4.append(", interactionType: ");
                sbM4.append(int12);
                sbM4.append(", minVelocityPxPerSecond: ");
                sbM4.append(double1);
                sbM4.append(" expansionOverHalf: ");
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM4, bool14, ", allowExpandForSmallExpansion: ", bool24);
            case 9:
                boolean bool15 = logMessage.getBool1();
                boolean bool25 = logMessage.getBool2();
                boolean bool34 = logMessage.getBool3();
                StringBuilder sbM5 = EmergencyButtonController$$ExternalSyntheticOutline0.m("PulsingGestureListener#onSingleTapUp all of this must true for single tap to be detected: isDozing: ", ", singleTapEnabled: ", ", isNotDocked: ", bool15, bool25);
                sbM5.append(bool34);
                return sbM5.toString();
            case 10:
                return "NSWVC: touch not dispatched: isTrackingBarGesture: " + logMessage.getBool1() + ", isExpandAnimationRunning: " + logMessage.getBool2();
            case 11:
                return "PulsingGestureListener#onSingleTapUp all of this must true for single tap to be detected: proximityIsNotNear: " + logMessage.getBool1() + ", isNotFalseTap: " + logMessage.getBool2();
            case 12:
                return logMessage.getStr1() + " qsExpanded=" + logMessage.getBool1() + ",qsMinExpansionHeight=" + logMessage.getInt1() + ",qsMaxExpansionHeight=" + logMessage.getInt2() + ",stackScrollerOverscrolling=" + logMessage.getBool2() + ",qsAnimatorExpand=" + logMessage.getBool3() + ",animatingQs=" + logMessage.getLong1();
            case 13:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("qsExpandImmediate=", logMessage.getBool1());
            case 14:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("New panel State: ", logMessage.getStr1());
            case 15:
                String str12 = logMessage.getStr1();
                long long12 = logMessage.getLong1();
                long long22 = logMessage.getLong2();
                double double12 = logMessage.getDouble1();
                int int13 = logMessage.getInt1();
                int int22 = logMessage.getInt2();
                return str12 + "\neventTime=" + long12 + ",downTime=" + long22 + ",y=" + double12 + ",action=" + int13 + ",statusBarState=" + (int22 != 0 ? int22 != 1 ? int22 != 2 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt2(), "UNKNOWN:") : "SHADE_LOCKED" : "KEYGUARD" : "SHADE");
            case 16:
                String str13 = logMessage.getStr1();
                int int14 = logMessage.getInt1();
                int int23 = logMessage.getInt2();
                long long13 = logMessage.getLong1();
                double double13 = logMessage.getDouble1();
                boolean bool16 = logMessage.getBool1();
                boolean bool26 = logMessage.getBool2();
                boolean bool35 = logMessage.getBool3();
                StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(int14, "QsTrackingNotStarted: downTime=", str13, ",initTouchY=", ",y=");
                sbM890m.append(int23);
                sbM890m.append(",h=");
                sbM890m.append(long13);
                sbM890m.append(",slop=");
                sbM890m.append(double13);
                sbM890m.append(",qsExpanded=");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM890m, bool16, ",keyguardShowing=", bool26, ",qsExpansion=");
                sbM890m.append(bool35);
                return sbM890m.toString();
            default:
                return logMessage.getStr1() + ": eventTime=" + logMessage.getLong1() + ",downTime=" + logMessage.getLong2() + ",action=" + logMessage.getInt1() + ",class=" + logMessage.getInt2();
        }
    }
}
