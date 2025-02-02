package com.android.systemui.plugins.aod;

import android.graphics.drawable.Icon;
import android.service.notification.StatusBarNotification;
import android.widget.ImageView;
import com.android.systemui.plugins.annotations.VersionCheck;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PluginAODNotificationManager extends PluginAODBaseManager {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void animateExpandLockedShadePanel(StatusBarNotification statusBarNotification);

        void clickNotification(String str);

        Icon getNotificationIcon(String str);

        void requestActiveNotifications();

        void requestVisibleNotifications();

        void showSubScreenNotification(String str);
    }

    void addNotification(StatusBarNotification statusBarNotification);

    @VersionCheck(version = 1050)
    void lockscreenNotificationTypeChanged(int i);

    void removeNotification(String str);

    void setCallback(Callback callback);

    void setTagId(int i, int i2);

    @VersionCheck(version = 1050)
    void subLauncherUpdateNotification(ArrayList<ImageView> arrayList);

    void updateActiveNotifications(List<StatusBarNotification> list);

    void updateNotification(StatusBarNotification statusBarNotification);

    void updateNotification(ArrayList<ImageView> arrayList);

    void updateVisibleNotifications(List<StatusBarNotification> list, List<StatusBarNotification> list2, int i);
}
