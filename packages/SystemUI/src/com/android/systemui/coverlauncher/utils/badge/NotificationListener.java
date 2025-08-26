package com.android.systemui.coverlauncher.utils.badge;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
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

/* loaded from: classes2.dex */
public final class NotificationListener extends NotificationListenerService {
    public static final Companion Companion = new Companion(null);
    public static final HashSet sBlockChannelSet = new HashSet(CollectionsKt__CollectionsKt.mutableListOf("CHANNEL_ID_RECORDING_SCREEN", "voice_note_notification_channel"));
    public Context mContext;
    public final NotificationListenerService.Ranking mTempRanking = new NotificationListenerService.Ranking();
    public boolean mIsRegister = true;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        boolean zShouldBeFilteredOut = shouldBeFilteredOut(statusBarNotification);
        int iSemGetIdentifier = statusBarNotification.getUser().semGetIdentifier();
        NotificationItem notificationItem = new NotificationItem(statusBarNotification.getKey(), getTargetActivity(statusBarNotification) + ":" + iSemGetIdentifier, statusBarNotification.getNotification().number);
        StringBuilder sb = new StringBuilder("updateBadge item=");
        sb.append(notificationItem);
        Log.i("CoverLauncher_NotificationListener", sb.toString());
        BadgeManager.Companion.getClass();
        BadgeManager.Companion.getInstance();
        BadgeManager companion = BadgeManager.Companion.getInstance();
        BadgeItem badgeItem = (BadgeItem) companion.items.get(notificationItem.info);
        if (badgeItem == null) {
            if (zShouldBeFilteredOut) {
                return;
            }
            BadgeItem badgeItem2 = new BadgeItem(notificationItem.info);
            badgeItem2.addOrUpdateNotificationItem(notificationItem);
            companion.addItem(notificationItem.info, badgeItem2);
            return;
        }
        if (!zShouldBeFilteredOut) {
            badgeItem.addOrUpdateNotificationItem(notificationItem);
        } else if (((ArrayList) badgeItem.mNotificationItems).remove(notificationItem)) {
            badgeItem.mTotalCount -= notificationItem.count;
        }
        if (((ArrayList) badgeItem.mNotificationItems).isEmpty()) {
            String str = notificationItem.info;
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("remove item, key : ", str, "CoverLauncher_BadgeManager");
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
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("NotificationListener onListenerConnected mIsRegister=", "CoverLauncher_NotificationListener", this.mIsRegister);
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
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("onNotificationFullRefresh mIsRegister=", "CoverLauncher_NotificationListener", this.mIsRegister);
        if (this.mIsRegister) {
            try {
                StatusBarNotification[] activeNotifications = getActiveNotifications(1);
                HashSet hashSet = new HashSet();
                int length = activeNotifications.length;
                int i = 0;
                for (int i2 = 0; i2 < length; i2++) {
                    StatusBarNotification statusBarNotification = activeNotifications[i2];
                    if (statusBarNotification != null && shouldBeFilteredOut(statusBarNotification)) {
                        hashSet.add(Integer.valueOf(i2));
                    }
                }
                ArrayList arrayList = new ArrayList(activeNotifications.length - hashSet.size());
                int length2 = activeNotifications.length;
                for (int i3 = 0; i3 < length2; i3++) {
                    if (!hashSet.contains(Integer.valueOf(i3))) {
                        arrayList.add(activeNotifications[i3]);
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                int size = arrayList.size();
                int i4 = 0;
                while (i4 < size) {
                    Object obj = arrayList.get(i4);
                    i4++;
                    StatusBarNotification statusBarNotification2 = (StatusBarNotification) obj;
                    if (statusBarNotification2 != null && !shouldBeFilteredOut(statusBarNotification2)) {
                        int iSemGetIdentifier = statusBarNotification2.getUser().semGetIdentifier();
                        arrayList2.add(new NotificationItem(statusBarNotification2.getKey(), getTargetActivity(statusBarNotification2) + ":" + iSemGetIdentifier, statusBarNotification2.getNotification().number));
                    }
                }
                BadgeManager.Companion.getClass();
                BadgeManager.Companion.getInstance();
                BadgeManager companion = BadgeManager.Companion.getInstance();
                HashMap map = companion.items;
                HashMap map2 = new HashMap(map);
                int size2 = arrayList2.size();
                while (i < size2) {
                    Object obj2 = arrayList2.get(i);
                    i++;
                    NotificationItem notificationItem = (NotificationItem) obj2;
                    BadgeItem badgeItem = (BadgeItem) companion.items.get(notificationItem.info);
                    if (badgeItem == null) {
                        badgeItem = new BadgeItem(notificationItem.info);
                        companion.addItem(notificationItem.info, badgeItem);
                    }
                    badgeItem.addOrUpdateNotificationItem(notificationItem);
                }
                for (String str : map.keySet()) {
                    BadgeItem badgeItem2 = (BadgeItem) map2.get(str);
                    BadgeItem badgeItem3 = (BadgeItem) companion.items.get(str);
                    if (badgeItem2 == null) {
                        map2.put(str, badgeItem3);
                    } else {
                        if (Intrinsics.areEqual(badgeItem2.mInfo, badgeItem3 != null ? badgeItem3.mInfo : null)) {
                            if (badgeItem3 != null) {
                                int i5 = badgeItem2.mTotalCount;
                                if (i5 > 999) {
                                    i5 = 999;
                                }
                                int i6 = badgeItem3.mTotalCount;
                                if (i5 == (i6 <= 999 ? i6 : 999)) {
                                }
                            }
                        }
                        map2.remove(str);
                    }
                }
                map2.isEmpty();
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
        int iSemGetIdentifier = statusBarNotification.getUser().semGetIdentifier();
        NotificationItem notificationItem = new NotificationItem(statusBarNotification.getKey(), getTargetActivity(statusBarNotification) + ":" + iSemGetIdentifier, statusBarNotification.getNotification().number);
        BadgeManager.Companion.getClass();
        BadgeManager.Companion.getInstance();
        BadgeManager companion = BadgeManager.Companion.getInstance();
        BadgeItem badgeItem = (BadgeItem) companion.items.get(notificationItem.info);
        if (badgeItem != null) {
            boolean zRemove = ((ArrayList) badgeItem.mNotificationItems).remove(notificationItem);
            if (zRemove) {
                badgeItem.mTotalCount -= notificationItem.count;
            }
            if (zRemove && ((ArrayList) badgeItem.mNotificationItems).isEmpty()) {
                String str = notificationItem.info;
                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("remove item, key : ", str, "CoverLauncher_BadgeManager");
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
