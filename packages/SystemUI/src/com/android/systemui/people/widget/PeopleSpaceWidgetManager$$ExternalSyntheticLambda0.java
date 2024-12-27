package com.android.systemui.people.widget;

import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.service.notification.NotificationListenerService;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.people.NotificationHelper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class PeopleSpaceWidgetManager$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PeopleSpaceWidgetManager$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        NotificationListenerService.Ranking ranking;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                PeopleSpaceWidgetManager peopleSpaceWidgetManager = (PeopleSpaceWidgetManager) obj2;
                NotificationEntry notificationEntry = (NotificationEntry) obj;
                peopleSpaceWidgetManager.getClass();
                NotificationHelper.AnonymousClass1 anonymousClass1 = NotificationHelper.notificationEntryComparator;
                if (notificationEntry != null && (ranking = notificationEntry.mRanking) != null && ranking.getConversationShortcutInfo() != null && notificationEntry.mSbn.getNotification() != null && NotificationHelper.isMissedCallOrHasContent(notificationEntry)) {
                    Optional optional = peopleSpaceWidgetManager.mBubblesOptional;
                    try {
                        if (optional.isPresent()) {
                            if (((BubbleController.BubblesImpl) ((Bubbles) optional.get())).isBubbleNotificationSuppressedFromShade(notificationEntry.mKey, notificationEntry.mSbn.getGroupKey())) {
                            }
                        }
                    } catch (Exception e) {
                        EmergencyButton$$ExternalSyntheticOutline0.m("Exception checking if notification is suppressed: ", e, "PeopleNotifHelper");
                    }
                    return true;
                }
                return false;
            default:
                return ((AppWidgetProviderInfo) obj).provider.equals((ComponentName) obj2);
        }
    }
}
