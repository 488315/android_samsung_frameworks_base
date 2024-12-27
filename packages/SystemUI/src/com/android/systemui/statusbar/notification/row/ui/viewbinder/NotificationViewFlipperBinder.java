package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import android.widget.ViewFlipper;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.NotificationViewFlipperViewModel;
import kotlin.coroutines.EmptyCoroutineContext;

public final class NotificationViewFlipperBinder {
    public static final NotificationViewFlipperBinder INSTANCE = new NotificationViewFlipperBinder();

    private NotificationViewFlipperBinder() {
    }

    public static void bindWhileAttached(ViewFlipper viewFlipper, NotificationViewFlipperViewModel notificationViewFlipperViewModel) {
        if (viewFlipper.isAutoStart()) {
            RepeatWhenAttachedKt.repeatWhenAttached(viewFlipper, EmptyCoroutineContext.INSTANCE, new NotificationViewFlipperBinder$bindWhileAttached$2(viewFlipper, notificationViewFlipperViewModel, null));
        }
    }
}
