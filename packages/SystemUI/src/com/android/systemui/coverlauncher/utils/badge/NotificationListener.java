package com.android.systemui.coverlauncher.utils.badge;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.utils.badge.BadgeManager;
import com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationListener extends NotificationListenerService {
    public static final Companion Companion = new Companion(null);
    public static final HashSet sBlockChannelSet = new HashSet(CollectionsKt__CollectionsKt.mutableListOf("CHANNEL_ID_RECORDING_SCREEN", "voice_note_notification_channel"));
    public Context mContext;
    public final NotificationListenerService.Ranking mTempRanking = new NotificationListenerService.Ranking();
    public boolean mIsRegister = true;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public NotificationListener(Context context) {
        this.mContext = context;
    }

    public static String getTargetActivity(StatusBarNotification statusBarNotification) {
        Notification notification2 = statusBarNotification.getNotification();
        if (notification2 == null) {
            return statusBarNotification.getPackageName();
        }
        try {
            ComponentName componentName = (ComponentName) notification2.getClass().getField("semBadgeTarget").get(notification2);
            if (componentName != null) {
                return componentName.getPackageName() + "/" + componentName.getClassName();
            }
        } catch (IllegalAccessException e) {
            Log.e("CoverLauncher_NotificationListener", e.getMessage(), e);
        } catch (IllegalArgumentException e2) {
            Log.e("CoverLauncher_NotificationListener", e2.getMessage(), e2);
        } catch (NoSuchFieldException e3) {
            Log.e("CoverLauncher_NotificationListener", e3.getMessage(), e3);
        }
        return statusBarNotification.getPackageName();
    }

    private final void updateBadge(StatusBarNotification statusBarNotification) {
        boolean shouldBeFilteredOut = shouldBeFilteredOut(statusBarNotification);
        int semGetIdentifier = statusBarNotification.getUser().semGetIdentifier();
        NotificationItem notificationItem = new NotificationItem(statusBarNotification.getKey(), getTargetActivity(statusBarNotification) + ":" + semGetIdentifier, statusBarNotification.getNotification().number);
        StringBuilder sb = new StringBuilder("updateBadge item=");
        sb.append(notificationItem);
        Log.i("CoverLauncher_NotificationListener", sb.toString());
        BadgeManager.Companion.getClass();
        BadgeManager.Companion.getInstance();
        BadgeManager companion = BadgeManager.Companion.getInstance();
        BadgeItem badgeItem = (BadgeItem) companion.items.get(notificationItem.info);
        if (badgeItem == null) {
            if (shouldBeFilteredOut) {
                return;
            }
            BadgeItem badgeItem2 = new BadgeItem(notificationItem.info);
            badgeItem2.addOrUpdateNotificationItem(notificationItem);
            companion.addItem(notificationItem.info, badgeItem2);
            return;
        }
        if (!shouldBeFilteredOut) {
            badgeItem.addOrUpdateNotificationItem(notificationItem);
        } else if (((ArrayList) badgeItem.mNotificationItems).remove(notificationItem)) {
            badgeItem.mTotalCount -= notificationItem.count;
        }
        if (((ArrayList) badgeItem.mNotificationItems).isEmpty()) {
            String str = notificationItem.info;
            Log.i("CoverLauncher_BadgeManager", "remove item, key : ".concat(str));
            companion.items.remove(str);
        }
    }

    @Override // android.app.Service
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("Dump CoverLauncher_NotificationListener");
        printWriter.print("mIsRegister=" + this.mIsRegister);
        BadgeManager.Companion.getClass();
        Iterator it = BadgeManager.Companion.getInstance().items.entrySet().iterator();
        while (it.hasNext()) {
            printWriter.println("BadgeItem : ".concat(String.valueOf((BadgeItem) ((Map.Entry) it.next()).getValue())));
        }
    }

    @Override // android.app.Service
    public final void onCreate() {
        Log.i("CoverLauncher_NotificationListener", "NotificationListener onCreate");
        super.onCreate();
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public final void onDestroy() {
        Log.i("CoverLauncher_NotificationListener", "NotificationListener onDestroy");
        super.onDestroy();
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onListenerConnected() {
        SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("NotificationListener onListenerConnected mIsRegister=", "CoverLauncher_NotificationListener", this.mIsRegister);
        super.onListenerConnected();
        if (this.mIsRegister) {
            try {
                setOnNotificationPostedTrim(1);
                onNotificationFullRefresh();
                CoverLauncherWidgetViewController.Companion companion = CoverLauncherWidgetViewController.Companion;
                Context context = this.mContext;
                companion.getClass();
                CoverLauncherWidgetViewController.Companion.getInstance(context).updateAppWidget(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onListenerDisconnected() {
        Log.i("CoverLauncher_NotificationListener", "NotificationListener onListenerDisconnected");
        super.onListenerDisconnected();
        BadgeManager.Companion.getClass();
        BadgeManager.Companion.getInstance().items.clear();
    }

    public final void onNotificationFullRefresh() {
        SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("onNotificationFullRefresh mIsRegister=", "CoverLauncher_NotificationListener", this.mIsRegister);
        if (this.mIsRegister) {
            try {
                StatusBarNotification[] activeNotifications = getActiveNotifications(1);
                HashSet hashSet = new HashSet();
                int length = activeNotifications.length;
                for (int i = 0; i < length; i++) {
                    StatusBarNotification statusBarNotification = activeNotifications[i];
                    if (statusBarNotification != null && shouldBeFilteredOut(statusBarNotification)) {
                        hashSet.add(Integer.valueOf(i));
                    }
                }
                ArrayList arrayList = new ArrayList(activeNotifications.length - hashSet.size());
                int length2 = activeNotifications.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    if (!hashSet.contains(Integer.valueOf(i2))) {
                        arrayList.add(activeNotifications[i2]);
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    StatusBarNotification statusBarNotification2 = (StatusBarNotification) it.next();
                    if (statusBarNotification2 != null && !shouldBeFilteredOut(statusBarNotification2)) {
                        int semGetIdentifier = statusBarNotification2.getUser().semGetIdentifier();
                        arrayList2.add(new NotificationItem(statusBarNotification2.getKey(), getTargetActivity(statusBarNotification2) + ":" + semGetIdentifier, statusBarNotification2.getNotification().number));
                    }
                }
                BadgeManager.Companion.getClass();
                BadgeManager.Companion.getInstance();
                BadgeManager companion = BadgeManager.Companion.getInstance();
                HashMap hashMap = companion.items;
                HashMap hashMap2 = new HashMap(hashMap);
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    NotificationItem notificationItem = (NotificationItem) it2.next();
                    BadgeItem badgeItem = (BadgeItem) companion.items.get(notificationItem.info);
                    if (badgeItem == null) {
                        badgeItem = new BadgeItem(notificationItem.info);
                        companion.addItem(notificationItem.info, badgeItem);
                    }
                    badgeItem.addOrUpdateNotificationItem(notificationItem);
                }
                for (String str : hashMap.keySet()) {
                    BadgeItem badgeItem2 = (BadgeItem) hashMap2.get(str);
                    Intrinsics.checkNotNull(str);
                    BadgeItem badgeItem3 = (BadgeItem) companion.items.get(str);
                    if (badgeItem2 == null) {
                        hashMap2.put(str, badgeItem3);
                    } else {
                        if (Intrinsics.areEqual(badgeItem2.mInfo, badgeItem3 != null ? badgeItem3.mInfo : null)) {
                            if (badgeItem3 != null) {
                                int i3 = badgeItem2.mTotalCount;
                                if (i3 > 999) {
                                    i3 = 999;
                                }
                                int i4 = badgeItem3.mTotalCount;
                                if (i3 == (i4 <= 999 ? i4 : 999)) {
                                }
                            }
                        }
                        hashMap2.remove(str);
                    }
                }
                hashMap2.isEmpty();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationPosted(StatusBarNotification statusBarNotification) {
        super.onNotificationPosted(statusBarNotification);
        if (statusBarNotification == null) {
            return;
        }
        HashSet hashSet = sBlockChannelSet;
        Notification notification2 = statusBarNotification.getNotification();
        if (hashSet.contains(notification2 != null ? notification2.getChannelId() : null)) {
            return;
        }
        updateBadge(statusBarNotification);
        CoverLauncherWidgetViewController.Companion companion = CoverLauncherWidgetViewController.Companion;
        Context context = this.mContext;
        companion.getClass();
        CoverLauncherWidgetViewController.Companion.getInstance(context).updateAppWidget(true);
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        super.onNotificationRankingUpdate(rankingMap);
        Log.i("CoverLauncher_NotificationListener", "onNotificationRankingUpdate");
        BadgeManager.Companion.getClass();
        BadgeManager.Companion.getInstance().items.clear();
        onNotificationFullRefresh();
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        super.onNotificationRemoved(statusBarNotification);
        if (statusBarNotification == null) {
            return;
        }
        int semGetIdentifier = statusBarNotification.getUser().semGetIdentifier();
        NotificationItem notificationItem = new NotificationItem(statusBarNotification.getKey(), getTargetActivity(statusBarNotification) + ":" + semGetIdentifier, statusBarNotification.getNotification().number);
        BadgeManager.Companion.getClass();
        BadgeManager.Companion.getInstance();
        BadgeManager companion = BadgeManager.Companion.getInstance();
        BadgeItem badgeItem = (BadgeItem) companion.items.get(notificationItem.info);
        if (badgeItem != null) {
            boolean remove = ((ArrayList) badgeItem.mNotificationItems).remove(notificationItem);
            if (remove) {
                badgeItem.mTotalCount -= notificationItem.count;
            }
            if (remove && ((ArrayList) badgeItem.mNotificationItems).isEmpty()) {
                String str = notificationItem.info;
                Log.i("CoverLauncher_BadgeManager", "remove item, key : ".concat(str));
                companion.items.remove(str);
            }
        }
        CoverLauncherWidgetViewController.Companion companion2 = CoverLauncherWidgetViewController.Companion;
        Context context = this.mContext;
        companion2.getClass();
        CoverLauncherWidgetViewController.Companion.getInstance(context).updateAppWidget(true);
    }

    public final boolean shouldBeFilteredOut(StatusBarNotification statusBarNotification) {
        getCurrentRanking().getRanking(statusBarNotification.getKey(), this.mTempRanking);
        if (!this.mTempRanking.canShowBadge()) {
            return true;
        }
        Notification notification2 = statusBarNotification.getNotification();
        if (!Intrinsics.areEqual(this.mTempRanking.getChannel().getId(), "miscellaneous") || (notification2.flags & 2) == 0) {
            return ((notification2.flags & 512) != 0) || (TextUtils.isEmpty(notification2.extras.getCharSequence("android.title")) && TextUtils.isEmpty(notification2.extras.getCharSequence("android.text")));
        }
        return true;
    }
}
