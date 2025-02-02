package com.android.systemui.people.widget;

import android.content.ComponentName;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.systemui.people.PeopleSpaceUtils;
import java.util.Collection;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleSpaceWidgetManager$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ PeopleSpaceWidgetManager f$0;
    public final /* synthetic */ StatusBarNotification f$1;
    public final /* synthetic */ PeopleSpaceUtils.NotificationAction f$2;
    public final /* synthetic */ Collection f$3;

    public /* synthetic */ PeopleSpaceWidgetManager$$ExternalSyntheticLambda6(PeopleSpaceWidgetManager peopleSpaceWidgetManager, StatusBarNotification statusBarNotification, PeopleSpaceUtils.NotificationAction notificationAction, Collection collection) {
        this.f$0 = peopleSpaceWidgetManager;
        this.f$1 = statusBarNotification;
        this.f$2 = notificationAction;
        this.f$3 = collection;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.f$0;
        StatusBarNotification statusBarNotification = this.f$1;
        PeopleSpaceUtils.NotificationAction notificationAction = this.f$2;
        Collection collection = this.f$3;
        peopleSpaceWidgetManager.getClass();
        try {
            PeopleTileKey peopleTileKey = new PeopleTileKey(statusBarNotification.getShortcutId(), statusBarNotification.getUser().getIdentifier(), statusBarNotification.getPackageName());
            if (PeopleTileKey.isValid(peopleTileKey)) {
                if (peopleSpaceWidgetManager.mAppWidgetManager.getAppWidgetIds(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class)).length == 0) {
                    Log.d("PeopleSpaceWidgetMgr", "No app widget ids returned");
                    return;
                }
                synchronized (peopleSpaceWidgetManager.mLock) {
                    Set matchingKeyWidgetIds = peopleSpaceWidgetManager.getMatchingKeyWidgetIds(peopleTileKey);
                    matchingKeyWidgetIds.addAll(peopleSpaceWidgetManager.getMatchingUriWidgetIds(statusBarNotification, notificationAction));
                    peopleSpaceWidgetManager.updateWidgetIdsBasedOnNotifications(matchingKeyWidgetIds, collection);
                }
            }
        } catch (Exception e) {
            Log.e("PeopleSpaceWidgetMgr", "updateWidgetsWithNotificationChangedInBackground failing", e);
        }
    }
}
