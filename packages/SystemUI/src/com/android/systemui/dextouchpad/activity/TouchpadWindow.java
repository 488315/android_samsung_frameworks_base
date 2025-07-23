package com.android.systemui.dextouchpad.activity;

import android.content.Context;
import android.widget.Toast;
import com.android.systemui.dextouchpad.manager.notification.NotificationType;
import com.android.systemui.dextouchpad.manager.notification.TouchpadNotificationManager;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TouchpadWindow extends FloatingWindow {
    public int mSpenMode;
    public Toast mSpenNotSupportedToast;
    public final AtomicBoolean mSpenNotSupportedToastBlocked;
    public final AtomicBoolean mSpenNotSupportedToastShown;
    public final AnonymousClass1 mToastCallback;
    public final TouchpadNotificationManager mTouchpadNotificationManager;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.dextouchpad.activity.TouchpadWindow$1] */
    public TouchpadWindow(int i, String str, int i2, AtomicBoolean atomicBoolean, Context context) {
        super(i, str, i2);
        this.mToastCallback = new Toast.Callback() { // from class: com.android.systemui.dextouchpad.activity.TouchpadWindow.1
            @Override // android.widget.Toast.Callback
            public final void onToastHidden() {
                TouchpadWindow.this.mSpenNotSupportedToastShown.set(false);
                Toast toast = TouchpadWindow.this.mSpenNotSupportedToast;
                if (toast != null) {
                    toast.removeCallback(this);
                }
            }

            @Override // android.widget.Toast.Callback
            public final void onToastShown() {
                TouchpadWindow.this.mSpenNotSupportedToastShown.set(true);
            }
        };
        this.mSpenNotSupportedToastShown = new AtomicBoolean(false);
        this.mSpenNotSupportedToastBlocked = atomicBoolean;
        this.mTouchpadNotificationManager = TouchpadNotificationManager.getsInstance(context);
    }

    @Override // com.android.systemui.dextouchpad.activity.FloatingWindow, com.android.systemui.dextouchpad.activity.ViewPositionTracker
    public final void onStartTearDown() {
        unsetOnHoverListener();
        TouchpadNotificationManager touchpadNotificationManager = this.mTouchpadNotificationManager;
        if (touchpadNotificationManager != null) {
            NotificationType notificationType = NotificationType.SPEN;
            if (((HashMap) touchpadNotificationManager.mActiveNotifications).containsKey(notificationType) && !this.mActivity.isChangingConfigurations()) {
                touchpadNotificationManager.remove(notificationType);
            }
        }
        super.onStartTearDown();
    }

    @Override // com.android.systemui.dextouchpad.activity.FloatingWindow, com.android.systemui.dextouchpad.activity.ViewPositionTracker
    public final void onStopWindow() {
        TouchpadNotificationManager touchpadNotificationManager = this.mTouchpadNotificationManager;
        if (touchpadNotificationManager != null) {
            NotificationType notificationType = NotificationType.SPEN;
            if (((HashMap) touchpadNotificationManager.mActiveNotifications).containsKey(notificationType) && !this.mActivity.isChangingConfigurations()) {
                touchpadNotificationManager.remove(notificationType);
            }
        }
        unsetOnHoverListener();
    }

    public final void unsetOnHoverListener() {
        this.mWindowView.setOnHoverListener(null);
        Toast toast = this.mSpenNotSupportedToast;
        if (toast != null) {
            toast.cancel();
        }
    }
}
