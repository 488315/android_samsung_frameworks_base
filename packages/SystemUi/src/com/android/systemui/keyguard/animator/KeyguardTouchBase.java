package com.android.systemui.keyguard.animator;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class KeyguardTouchBase {
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG = Log.isLoggable("KeyguardTouchBase", 3);
    public final Context context;
    public float distance;
    public boolean hasDozeAmount;
    public boolean intercepting;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
            StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("canBeUnlock touchStart=(", f, ", ", f2, "), multiTouch=");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m88m, z3, ", unlockExecuted=", z4, ", deviceInteractive=");
            m88m.append(z);
            com.android.systemui.keyguard.Log.m138d("KeyguardTouchBase", m88m.toString());
        }
        return z2;
    }

    public final void initDimens() {
        DisplayInfo displayInfo = new DisplayInfo();
        Context context = this.context;
        context.getDisplay().getDisplayInfo(displayInfo);
        updateAffordace(displayInfo);
        this.longPressAllowHeight = (int) context.getResources().getDimension(R.dimen.notification_panel_margin_bottom);
        float min = Math.min(displayInfo.logicalWidth / displayInfo.physicalXDpi, displayInfo.logicalHeight / displayInfo.physicalYDpi);
        this.swipeUnlockRadius = (int) (Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight) * (min > 4.5f ? 0.3f : min > 3.5f ? 0.4f : min > 1.8f ? 0.5f : 0.7f));
        int scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.touchSlop = scaledTouchSlop;
        this.lockEditorTouchSlop = context.getResources().getDimensionPixelSize(R.dimen.lock_ui_edit_touch_slop_delta) + scaledTouchSlop;
    }

    public final void setIntercept(boolean z) {
        if (this.intercepting != z) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setIntercept ", z, "KeyguardTouchBase");
        }
        this.intercepting = z;
    }

    public final void setTouch(boolean z) {
        if (this.isTouching != z) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setTouch ", z, "KeyguardTouchBase");
        }
        this.isTouching = z;
    }

    public final void updateDistance(MotionEvent motionEvent, boolean z) {
        this.updateDistanceCount++;
        PointF pointF = this.lastMovePos;
        pointF.x = motionEvent.getRawX();
        pointF.y = motionEvent.getRawY();
        PointF pointF2 = this.touchDownPos;
        if (!(pointF2.x == -1.0f)) {
            if (!(pointF2.y == -1.0f)) {
                float sqrt = (float) Math.sqrt(Math.pow(motionEvent.getRawY() - pointF2.y, 2.0d) + Math.pow(motionEvent.getRawX() - pointF2.x, 2.0d));
                this.distance = sqrt;
                if (DEBUG || Float.isNaN(sqrt)) {
                    float rawX = motionEvent.getRawX();
                    float rawY = motionEvent.getRawY();
                    float f = this.distance;
                    StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("updateDistance curX=", rawX, " curY=", rawY, " distance=");
                    m88m.append(f);
                    m88m.append(" fullScreen=");
                    m88m.append(z);
                    Log.d("KeyguardTouchBase", m88m.toString());
                    return;
                }
                return;
            }
        }
        this.distance = 0.0f;
        this.updateDistanceCount = 0;
    }

    public void updateAffordace(DisplayInfo displayInfo) {
    }
}
