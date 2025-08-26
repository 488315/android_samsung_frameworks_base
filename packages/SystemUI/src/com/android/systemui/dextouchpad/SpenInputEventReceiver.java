package com.android.systemui.dextouchpad;

import android.os.Looper;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.MotionEvent;
import com.android.systemui.dextouchpad.manager.notification.NotificationType;
import com.android.systemui.dextouchpad.manager.notification.TouchpadNotificationManager;
import com.android.systemui.dextouchpad.util.Features;
import com.android.systemui.dextouchpad.util.Utils;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class SpenInputEventReceiver extends InputEventReceiver {
    public final TouchpadNotificationManager mTouchpadNotificationManager;

    public SpenInputEventReceiver(InputChannel inputChannel, Looper looper, TouchpadNotificationManager touchpadNotificationManager) {
        super(inputChannel, looper);
        this.mTouchpadNotificationManager = touchpadNotificationManager;
    }

    public final void onInputEvent(InputEvent inputEvent) {
        MotionEvent motionEvent;
        int action;
        if ((inputEvent instanceof MotionEvent) && (inputEvent.getSource() & 2) != 0 && (((action = (motionEvent = (MotionEvent) inputEvent).getAction()) == 9 || action == 0) && motionEvent.isFromSource(16386))) {
            TouchpadNotificationManager touchpadNotificationManager = this.mTouchpadNotificationManager;
            if (touchpadNotificationManager != null) {
                NotificationType notificationType = NotificationType.SPEN;
                if (!((HashMap) touchpadNotificationManager.mActiveNotifications).containsKey(notificationType) && Features.IS_SPEN_INBOX_MODEL && Utils.mIsTouchpadEnabled) {
                    this.mTouchpadNotificationManager.show(notificationType);
                }
            }
            Utils.mIsSpenDetached = true;
        }
        super.onInputEvent(inputEvent);
    }
}
