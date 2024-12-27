package com.android.systemui.statusbar.notification.collection.render;

public interface NotifShadeEventSource {
    void setNotifRemovedByUserCallback(Runnable runnable);

    void setShadeEmptiedCallback(Runnable runnable);
}
