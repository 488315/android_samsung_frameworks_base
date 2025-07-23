package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import android.widget.ViewFlipper;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.NotificationViewFlipperViewModel;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationViewFlipperBinder {
    public static final NotificationViewFlipperBinder INSTANCE = new NotificationViewFlipperBinder();

    private NotificationViewFlipperBinder() {
    }

    public static void bindWhileAttached(ViewFlipper viewFlipper, NotificationViewFlipperViewModel notificationViewFlipperViewModel) {
        if (viewFlipper.isAutoStart()) {
            RepeatWhenAttachedKt.repeatWhenAttached(viewFlipper, EmptyCoroutineContext.INSTANCE, new NotificationViewFlipperBinder$bindWhileAttached$2(viewFlipper, notificationViewFlipperViewModel, null));
        } else {
            NotificationViewFlipperBinder$bindWhileAttached$1 notificationViewFlipperBinder$bindWhileAttached$1 = NotificationViewFlipperBinder$bindWhileAttached$1.INSTANCE;
        }
    }
}
