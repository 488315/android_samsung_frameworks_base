package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class IndicatorTouchHandler {
    public int doubleTapCount;
    public boolean isTouchOnCallChip;
    public final KeyguardStateController keyguardStateController;
    public final KnoxStateMonitor knoxStateMonitor;
    public final Handler mainHandler;
    public final OngoingCallController ongoingCallController;
    public final PowerManager powerManager;
    public float touchDownX;
    public float touchDownY;
    public final Rect callChipRect = new Rect();
    public final Rect keyguardCallChipRect = new Rect();
    public final IndicatorTouchHandler$callChipLayoutChangeListener$1 callChipLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.IndicatorTouchHandler$callChipLayoutChangeListener$1
        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (i == i5 && i3 == i7) {
                return;
            }
            IndicatorTouchHandler.access$updateCallChipRect(IndicatorTouchHandler.this);
        }
    };
    public final IndicatorTouchHandler$ongoingCallListener$1 ongoingCallListener = new OngoingCallListener() { // from class: com.android.systemui.statusbar.phone.IndicatorTouchHandler$ongoingCallListener$1
        @Override // com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener
        public final void onOngoingCallStateChanged() {
            IndicatorTouchHandler indicatorTouchHandler = IndicatorTouchHandler.this;
            indicatorTouchHandler.callChipRect.setEmpty();
            indicatorTouchHandler.keyguardCallChipRect.setEmpty();
            OngoingCallController ongoingCallController = indicatorTouchHandler.ongoingCallController;
            boolean hasOngoingCall = ongoingCallController.hasOngoingCall();
            IndicatorTouchHandler$callChipLayoutChangeListener$1 indicatorTouchHandler$callChipLayoutChangeListener$1 = indicatorTouchHandler.callChipLayoutChangeListener;
            if (!hasOngoingCall) {
                View view = ongoingCallController.chipView;
                if (view != null) {
                    view.removeOnLayoutChangeListener(indicatorTouchHandler$callChipLayoutChangeListener$1);
                }
                View view2 = ongoingCallController.keyguardCallChipController.chipView;
                if (view2 != null) {
                    view2.removeOnLayoutChangeListener(indicatorTouchHandler$callChipLayoutChangeListener$1);
                    return;
                }
                return;
            }
            IndicatorTouchHandler.access$updateCallChipRect(indicatorTouchHandler);
            View view3 = ongoingCallController.keyguardCallChipController.chipView;
            if (view3 != null) {
                view3.addOnLayoutChangeListener(indicatorTouchHandler$callChipLayoutChangeListener$1);
            }
            View view4 = ongoingCallController.chipView;
            if (view4 != null) {
                view4.addOnLayoutChangeListener(indicatorTouchHandler$callChipLayoutChangeListener$1);
            }
        }
    };
    public final IndicatorTouchHandler$doubleTapTimeoutRunnable$1 doubleTapTimeoutRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.IndicatorTouchHandler$doubleTapTimeoutRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            IndicatorTouchHandler.this.doubleTapCount = 0;
        }
    };

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.IndicatorTouchHandler$callChipLayoutChangeListener$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.IndicatorTouchHandler$ongoingCallListener$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.statusbar.phone.IndicatorTouchHandler$doubleTapTimeoutRunnable$1] */
    public IndicatorTouchHandler(Handler handler, OngoingCallController ongoingCallController, KnoxStateMonitor knoxStateMonitor, KeyguardStateController keyguardStateController, PowerManager powerManager) {
        this.mainHandler = handler;
        this.ongoingCallController = ongoingCallController;
        this.knoxStateMonitor = knoxStateMonitor;
        this.keyguardStateController = keyguardStateController;
        this.powerManager = powerManager;
    }

    public static final void access$updateCallChipRect(IndicatorTouchHandler indicatorTouchHandler) {
        OngoingCallController ongoingCallController = indicatorTouchHandler.ongoingCallController;
        View view = ongoingCallController.chipView;
        if (view != null) {
            Rect rect = indicatorTouchHandler.callChipRect;
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            int i = iArr[0];
            rect.set(i, 0, view.getWidth() + i, view.getHeight() + iArr[1]);
        }
        View view2 = ongoingCallController.keyguardCallChipController.chipView;
        if (view2 != null) {
            Rect rect2 = indicatorTouchHandler.keyguardCallChipRect;
            int[] iArr2 = new int[2];
            view2.getLocationInWindow(iArr2);
            int i2 = iArr2[0];
            rect2.set(i2, 0, view2.getWidth() + i2, view2.getHeight() + iArr2[1]);
        }
        Log.d("IndicatorTouchHandler", "update keyguard rect=" + indicatorTouchHandler.keyguardCallChipRect + " call chip rect=" + indicatorTouchHandler.callChipRect);
    }
}
