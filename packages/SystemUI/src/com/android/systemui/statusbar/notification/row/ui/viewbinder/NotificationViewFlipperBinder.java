package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import android.widget.ViewFlipper;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.NotificationViewFlipperViewModel;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
