package com.android.systemui.statusbar.phone;

import android.content.ComponentName;
import android.content.Context;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.plugins.NotificationListenerController;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NotificationListenerWithPlugins extends NotificationListenerService implements PluginListener {
    public boolean mConnected;
    public final PluginManager mPluginManager;
    public final ArrayList mPlugins = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.NotificationListenerWithPlugins$1 */
    public final class C30841 implements NotificationListenerController.NotificationProvider {
        public C30841() {
        }

        @Override // com.android.systemui.plugins.NotificationListenerController.NotificationProvider
        public final void addNotification(StatusBarNotification statusBarNotification) {
            NotificationListenerWithPlugins.this.onNotificationPosted(statusBarNotification, getRankingMap());
        }

        @Override // com.android.systemui.plugins.NotificationListenerController.NotificationProvider
        public final StatusBarNotification[] getActiveNotifications() {
            return NotificationListenerWithPlugins.super.getActiveNotifications();
        }

        @Override // com.android.systemui.plugins.NotificationListenerController.NotificationProvider
        public final NotificationListenerService.RankingMap getRankingMap() {
            return NotificationListenerWithPlugins.super.getCurrentRanking();
        }

        @Override // com.android.systemui.plugins.NotificationListenerController.NotificationProvider
        public final void removeNotification(StatusBarNotification statusBarNotification) {
            NotificationListenerWithPlugins.this.onNotificationRemoved(statusBarNotification, getRankingMap());
        }

        @Override // com.android.systemui.plugins.NotificationListenerController.NotificationProvider
        public final void updateRanking() {
            NotificationListenerWithPlugins.this.onNotificationRankingUpdate(getRankingMap());
        }
    }

    public NotificationListenerWithPlugins(PluginManager pluginManager) {
        this.mPluginManager = pluginManager;
    }

    @Override // android.service.notification.NotificationListenerService
    public final StatusBarNotification[] getActiveNotifications() {
        StatusBarNotification[] activeNotifications = super.getActiveNotifications();
        Iterator it = this.mPlugins.iterator();
        while (it.hasNext()) {
            activeNotifications = ((NotificationListenerController) it.next()).getActiveNotifications(activeNotifications);
        }
        return activeNotifications;
    }

    @Override // android.service.notification.NotificationListenerService
    public final NotificationListenerService.RankingMap getCurrentRanking() {
        NotificationListenerService.RankingMap currentRanking = super.getCurrentRanking();
        Iterator it = this.mPlugins.iterator();
        while (it.hasNext()) {
            currentRanking = ((NotificationListenerController) it.next()).getCurrentRanking(currentRanking);
        }
        return currentRanking;
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginConnected(Plugin plugin, Context context) {
        NotificationListenerController notificationListenerController = (NotificationListenerController) plugin;
        this.mPlugins.add(notificationListenerController);
        if (this.mConnected) {
            notificationListenerController.onListenerConnected(new C30841());
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginDisconnected(Plugin plugin) {
        this.mPlugins.remove((NotificationListenerController) plugin);
    }

    public final void registerAsSystemService(Context context, ComponentName componentName, int i) {
        super.registerAsSystemService(context, componentName, i);
        this.mPluginManager.addPluginListener(this, NotificationListenerController.class);
    }

    public final void unregisterAsSystemService() {
        super.unregisterAsSystemService();
        this.mPluginManager.removePluginListener(this);
    }
}
