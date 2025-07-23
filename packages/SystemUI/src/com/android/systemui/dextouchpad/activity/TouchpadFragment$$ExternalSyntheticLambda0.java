package com.android.systemui.dextouchpad.activity;

import com.android.systemui.dextouchpad.manager.notification.NotificationType;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class TouchpadFragment$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TouchpadFragment f$0;

    public /* synthetic */ TouchpadFragment$$ExternalSyntheticLambda0(TouchpadFragment touchpadFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = touchpadFragment;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        TouchpadFragment touchpadFragment = this.f$0;
        switch (i) {
            case 0:
                touchpadFragment.mTouchpadNotificationManager.remove(NotificationType.TOUCHPAD);
                break;
            default:
                touchpadFragment.mTouchpadNotificationManager.show(NotificationType.TOUCHPAD);
                break;
        }
    }
}
