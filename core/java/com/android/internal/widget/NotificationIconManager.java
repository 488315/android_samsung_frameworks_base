package com.android.internal.widget;

import android.graphics.drawable.Icon;

public interface NotificationIconManager {
    Runnable updateIcon(NotificationDrawableConsumer notificationDrawableConsumer, Icon icon);
}
