package com.android.systemui.statusbar.notification.stack.ui.view;

import com.android.internal.statusbar.NotificationVisibility;

/* loaded from: classes3.dex */
public abstract class NotificationStatsLoggerImplKt {
    public static final NotificationVisibility.NotificationLocation access$toNotificationLocation(int i) {
        return i != 1 ? i != 2 ? i != 4 ? i != 8 ? i != 16 ? i != 64 ? NotificationVisibility.NotificationLocation.LOCATION_UNKNOWN : NotificationVisibility.NotificationLocation.LOCATION_GONE : NotificationVisibility.NotificationLocation.LOCATION_BOTTOM_STACK_HIDDEN : NotificationVisibility.NotificationLocation.LOCATION_BOTTOM_STACK_PEEKING : NotificationVisibility.NotificationLocation.LOCATION_MAIN_AREA : NotificationVisibility.NotificationLocation.LOCATION_HIDDEN_TOP : NotificationVisibility.NotificationLocation.LOCATION_FIRST_HEADS_UP;
    }

    public static /* synthetic */ void getUNKNOWN_RANK$annotations() {
    }
}
