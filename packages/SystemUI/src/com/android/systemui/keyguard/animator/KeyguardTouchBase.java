package com.android.systemui.keyguard.animator;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.samsung.android.knox.ex.knoxAI.KnoxAiManagerInternal;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class KeyguardTouchBase {
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG = Log.isLoggable("KeyguardTouchBase", 3);
    public final Context context;
    public float distance;
    public boolean hasDozeAmount;
    public boolean intercepting;
    public boolean isDraggingDownStarted;
    public boolean isMultiTouch;
    public boolean isTouching;
    public boolean isUnlockExecuted;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public int lockEditorTouchSlop;
    public int longPressAllowHeight;
    public int swipeUnlockRadius;
    public int touchSlop;
    public int updateDistanceCount;
    public long userActivityInvokedTime;
    public final PointF touchDownPos = new PointF(-1.0f, -1.0f);
    public final PointF lastMovePos = new PointF(-1.0f, -1.0f);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public KeyguardTouchBase(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.context = context;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    public final boolean getCanBeUnlock() {
        boolean z = this.keyguardUpdateMonitor.mDeviceInteractive;
        PointF pointF = this.touchDownPos;
        float f = pointF.x;
        boolean z2 = f >= 0.0f && pointF.y >= 0.0f && !this.isMultiTouch && !this.isUnlockExecuted && z;
        if (!z2) {
            float f2 = pointF.y;
            boolean z3 = this.isMultiTouch;
            boolean z4 = this.isUnlockExecuted;
            StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("canBeUnlock touchStart=(", f, ", ", f2, "), multiTouch=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z3, ", unlockExecuted=", z4, ", deviceInteractive=");
            sbM.append(z);
            com.android.systemui.keyguard.Log.d("KeyguardTouchBase", sbM.toString());
        }
        return z2;
    }

    public final void initDimens$5() {
        DisplayInfo displayInfo = new DisplayInfo();
        this.context.getDisplay().getDisplayInfo(displayInfo);
        updateAffordace(displayInfo);
        this.longPressAllowHeight = (int) this.context.getResources().getDimension(R.dimen.notification_panel_margin_bottom);
        float fMin = Math.min(displayInfo.logicalWidth / displayInfo.physicalXDpi, displayInfo.logicalHeight / displayInfo.physicalYDpi);
        this.swipeUnlockRadius = (int) (Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight) * (fMin > 4.5f ? 0.3f : fMin > 3.5f ? 0.4f : fMin > 1.8f ? 0.5f : 0.7f));
        int scaledTouchSlop = ViewConfiguration.get(this.context).getScaledTouchSlop();
        this.touchSlop = scaledTouchSlop;
        this.lockEditorTouchSlop = StrongAuthPopup$$ExternalSyntheticOutline0.m(this.context, R.dimen.lock_ui_edit_touch_slop_delta, scaledTouchSlop);
    }

    public final void setDraggingDownStarted(boolean z) {
        if (this.isDraggingDownStarted != z) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("setDraggingDownIntercept ", "KeyguardTouchBase", z);
        }
        this.isDraggingDownStarted = z;
    }

    public final void setIntercept(boolean z) {
        if (this.intercepting != z) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("setIntercept ", "KeyguardTouchBase", z);
        }
        this.intercepting = z;
    }

    public final void setTouch(boolean z) {
        if (this.isTouching != z) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("setTouch ", "KeyguardTouchBase", z);
        }
        this.isTouching = z;
    }

    public final void updateDistance(MotionEvent motionEvent, boolean z) {
        this.updateDistanceCount++;
        PointF pointF = this.lastMovePos;
        pointF.x = motionEvent.getRawX();
        pointF.y = motionEvent.getRawY();
        PointF pointF2 = this.touchDownPos;
        if (pointF2.x == -1.0f || pointF2.y == -1.0f) {
            this.distance = 0.0f;
            this.updateDistanceCount = 0;
            return;
        }
        float rawX = motionEvent.getRawX() - this.touchDownPos.x;
        float fSqrt = (float) Math.sqrt(Math.pow(motionEvent.getRawY() - this.touchDownPos.y, 2.0d) + Math.pow(rawX, 2.0d));
        this.distance = fSqrt;
        if (DEBUG || Float.isNaN(fSqrt)) {
            float rawX2 = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            float f = this.distance;
            StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("updateDistance curX=", rawX2, " curY=", rawY, " distance=");
            sbM.append(f);
            sbM.append(" fullScreen=");
            sbM.append(z);
            Log.d("KeyguardTouchBase", sbM.toString());
        }
    }

    public final void userActivityForMove(Runnable runnable) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - KnoxAiManagerInternal.CONN_MAX_WAIT_TIME > this.userActivityInvokedTime) {
            runnable.run();
            this.userActivityInvokedTime = jCurrentTimeMillis;
        }
    }

    public void updateAffordace(DisplayInfo displayInfo) {
    }
}
