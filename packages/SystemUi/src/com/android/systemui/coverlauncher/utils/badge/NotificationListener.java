package com.android.systemui.coverlauncher.utils.badge;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class NotificationListener extends NotificationListenerService {
    public static final HashSet sBlockChannelSet = new HashSet(Arrays.asList("CHANNEL_ID_RECORDING_SCREEN", "voice_note_notification_channel"));
    public Context mContext;
    public final NotificationListenerService.Ranking mTempRanking = new NotificationListenerService.Ranking();
    public boolean mIsRegister = true;

    public NotificationListener() {
        new Handler(Looper.getMainLooper());
    }

    public static String getTargetActivity(StatusBarNotification statusBarNotification) {
        ComponentName componentName;
        Notification notification2 = statusBarNotification.getNotification();
        if (notification2 == null) {
            return statusBarNotification.getPackageName();
        }
        try {
            Field field = notification2.getClass().getField("semBadgeTarget");
            if (field != null && (componentName = (ComponentName) field.get(notification2)) != null) {
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

    @Override // android.app.Service
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "Dump CoverLauncher_NotificationListener", "mIsRegister=");
        m75m.append(this.mIsRegister);
        printWriter.print(m75m.toString());
        HashMap hashMap = BadgeManager.getInstance().mItems;
        if (hashMap != null) {
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                printWriter.println("BadgeItem : " + ((BadgeItem) ((Map.Entry) it.next()).getValue()).toString());
            }
        }
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        Log.i("CoverLauncher_NotificationListener", "NotificationListener onCreate");
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public final void onDestroy() {
        Log.i("CoverLauncher_NotificationListener", "NotificationListener onDestroy");
        super.onDestroy();
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onListenerConnected() {
        NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder("NotificationListener onListenerConnected mIsRegister="), this.mIsRegister, "CoverLauncher_NotificationListener");
        super.onListenerConnected();
        if (this.mIsRegister) {
            try {
                setOnNotificationPostedTrim(1);
                onNotificationFullRefresh();
                CoverLauncherWidgetViewController.getInstance(this.mContext).notifyAppWidgetViewDataChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onListenerDisconnected() {
        Log.i("CoverLauncher_NotificationListener", "NotificationListener onListenerDisconnected");
        super.onListenerDisconnected();
        BadgeManager.getInstance().mItems.clear();
    }

    public final void onNotificationFullRefresh() {
        ArrayList arrayList;
        HashMap hashMap;
        Log.i("CoverLauncher_NotificationListener", "onNotificationFullRefresh mIsRegister=" + this.mIsRegister + ",forceNotify=true");
        if (this.mIsRegister) {
            try {
                StatusBarNotification[] activeNotifications = getActiveNotifications(1);
                if (activeNotifications == null) {
                    arrayList = null;
                } else {
                    HashSet hashSet = new HashSet();
                    for (int i = 0; i < activeNotifications.length; i++) {
                        StatusBarNotification statusBarNotification = activeNotifications[i];
                        if (statusBarNotification != null && shouldBeFilteredOut(statusBarNotification)) {
                            hashSet.add(Integer.valueOf(i));
                        }
                    }
                    ArrayList arrayList2 = new ArrayList(activeNotifications.length - hashSet.size());
                    for (int i2 = 0; i2 < activeNotifications.length; i2++) {
                        if (!hashSet.contains(Integer.valueOf(i2))) {
                            arrayList2.add(activeNotifications[i2]);
                        }
                    }
                    arrayList = arrayList2;
                }
                if (arrayList == null) {
                    return;
                }
                ArrayList arrayList3 = new ArrayList();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    StatusBarNotification statusBarNotification2 = (StatusBarNotification) it.next();
                    if (statusBarNotification2 != null && !shouldBeFilteredOut(statusBarNotification2)) {
                        arrayList3.add(new NotificationItem(statusBarNotification2.getKey(), getTargetActivity(statusBarNotification2) + ":" + statusBarNotification2.getUser().semGetIdentifier(), statusBarNotification2.getNotification().number));
                    }
                }
                if (this.mContext != null) {
                    BadgeManager.getInstance().getClass();
                    BadgeManager badgeManager = BadgeManager.getInstance();
                    HashMap hashMap2 = badgeManager.mItems;
                    HashMap hashMap3 = new HashMap(hashMap2);
                    Iterator it2 = arrayList3.iterator();
                    while (true) {
                        boolean hasNext = it2.hasNext();
                        hashMap = badgeManager.mItems;
                        if (!hasNext) {
                            break;
                        }
                        NotificationItem notificationItem = (NotificationItem) it2.next();
                        BadgeItem badgeItem = (BadgeItem) hashMap.get(notificationItem.info);
                        if (badgeItem == null) {
                            badgeItem = new BadgeItem(notificationItem.info);
                            badgeManager.addItem(badgeItem, notificationItem.info);
                        }
                        badgeItem.addOrUpdateNotificationItem(notificationItem);
                    }
                    for (String str : hashMap2.keySet()) {
                        BadgeItem badgeItem2 = (BadgeItem) hashMap3.get(str);
                        BadgeItem badgeItem3 = (BadgeItem) hashMap.get(str);
                        if (badgeItem2 == null) {
                            hashMap3.put(str, badgeItem3);
                        } else if (!(badgeItem2.mInfo.equals(badgeItem3.mInfo) && Math.min(badgeItem2.mTotalCount, 999) != Math.min(badgeItem3.mTotalCount, 999))) {
                            hashMap3.remove(str);
                        }
                    }
                    hashMap3.isEmpty();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationPosted(StatusBarNotification statusBarNotification) {
        super.onNotificationPosted(statusBarNotification);
        if (statusBarNotification == null || sBlockChannelSet.contains(statusBarNotification.getNotification().getChannelId())) {
            return;
        }
        updateBadge(statusBarNotification);
        CoverLauncherWidgetViewController.getInstance(this.mContext).notifyAppWidgetViewDataChanged();
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        super.onNotificationRankingUpdate(rankingMap);
        Log.i("CoverLauncher_NotificationListener", "onNotificationRankingUpdate");
        BadgeManager.getInstance().mItems.clear();
        onNotificationFullRefresh();
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        super.onNotificationRemoved(statusBarNotification);
        if (statusBarNotification == null) {
            return;
        }
        NotificationItem notificationItem = new NotificationItem(statusBarNotification.getKey(), getTargetActivity(statusBarNotification) + ":" + statusBarNotification.getUser().semGetIdentifier(), statusBarNotification.getNotification().number);
        if (this.mContext != null) {
            BadgeManager.getInstance().getClass();
            BadgeManager badgeManager = BadgeManager.getInstance();
            BadgeItem badgeItem = (BadgeItem) badgeManager.mItems.get(notificationItem.info);
            if (badgeItem != null) {
                List list = badgeItem.mNotificationItems;
                boolean remove = ((ArrayList) list).remove(notificationItem);
                if (remove) {
                    badgeItem.mTotalCount -= notificationItem.count;
                }
                if (remove && ((ArrayList) list).size() == 0) {
                    String str = notificationItem.info;
                    KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("remove item, key : ", str, "CoverLauncher_BadgeManager");
                    badgeManager.mItems.remove(str);
                }
            }
        }
        CoverLauncherWidgetViewController.getInstance(this.mContext).notifyAppWidgetViewDataChanged();
    }

    public final boolean shouldBeFilteredOut(StatusBarNotification statusBarNotification) {
        if (statusBarNotification == null) {
            return true;
        }
        getCurrentRanking().getRanking(statusBarNotification.getKey(), this.mTempRanking);
        if (!this.mTempRanking.canShowBadge()) {
            return true;
        }
        Notification notification2 = statusBarNotification.getNotification();
        if (!this.mTempRanking.getChannel().getId().equals("miscellaneous") || (notification2.flags & 2) == 0) {
            return ((notification2.flags & 512) != 0) || (TextUtils.isEmpty(notification2.extras.getCharSequence("android.title")) && TextUtils.isEmpty(notification2.extras.getCharSequence("android.text")));
        }
        return true;
    }

    public void updateBadge(StatusBarNotification statusBarNotification) {
        boolean shouldBeFilteredOut = shouldBeFilteredOut(statusBarNotification);
        NotificationItem notificationItem = new NotificationItem(statusBarNotification.getKey(), getTargetActivity(statusBarNotification) + ":" + statusBarNotification.getUser().semGetIdentifier(), statusBarNotification.getNotification().number);
        StringBuilder sb = new StringBuilder("updateBadge item=");
        sb.append(notificationItem);
        Log.i("CoverLauncher_NotificationListener", sb.toString());
        if (this.mContext != null) {
            BadgeManager badgeManager = BadgeManager.getInstance();
            Boolean valueOf = Boolean.valueOf(shouldBeFilteredOut);
            badgeManager.getClass();
            BadgeManager badgeManager2 = BadgeManager.getInstance();
            BadgeItem badgeItem = (BadgeItem) badgeManager2.mItems.get(notificationItem.info);
            if (badgeItem == null) {
                if (valueOf.booleanValue()) {
                    return;
                }
                BadgeItem badgeItem2 = new BadgeItem(notificationItem.info);
                badgeItem2.addOrUpdateNotificationItem(notificationItem);
                badgeManager2.addItem(badgeItem2, notificationItem.info);
                return;
            }
            boolean booleanValue = valueOf.booleanValue();
            List list = badgeItem.mNotificationItems;
            if (!booleanValue) {
                badgeItem.addOrUpdateNotificationItem(notificationItem);
            } else if (((ArrayList) list).remove(notificationItem)) {
                badgeItem.mTotalCount -= notificationItem.count;
            }
            if (((ArrayList) list).size() == 0) {
                String str = notificationItem.info;
                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("remove item, key : ", str, "CoverLauncher_BadgeManager");
                badgeManager2.mItems.remove(str);
            }
        }
    }
}
