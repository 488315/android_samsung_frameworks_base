package com.android.systemui.statusbar.notification.row;

import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl;
import com.android.systemui.statusbar.notification.row.NotificationConversationInfo;
import com.android.systemui.wmshell.BubblesManager;

public final /* synthetic */ class NotificationConversationInfo$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationConversationInfo$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                NotificationConversationInfo notificationConversationInfo = (NotificationConversationInfo) obj;
                OnUserInteractionCallback onUserInteractionCallback = notificationConversationInfo.mOnUserInteractionCallback;
                ((OnUserInteractionCallbackImpl) onUserInteractionCallback).mVisualStabilityCoordinator.temporarilyAllowSectionChanges(notificationConversationInfo.mEntry, SystemClock.uptimeMillis());
                break;
            default:
                NotificationConversationInfo.UpdateChannelRunnable updateChannelRunnable = (NotificationConversationInfo.UpdateChannelRunnable) obj;
                BubblesManager bubblesManager = (BubblesManager) NotificationConversationInfo.this.mBubblesManagerOptional.get();
                NotificationEntry notificationEntry = NotificationConversationInfo.this.mEntry;
                bubblesManager.getClass();
                if (notificationEntry.mBubbleMetadata != null) {
                    try {
                        bubblesManager.mBarService.onNotificationBubbleChanged(notificationEntry.mKey, true, 2);
                    } catch (RemoteException e) {
                        Log.e("Bubbles", e.getMessage());
                    }
                    bubblesManager.mShadeController.collapseShade(true);
                    ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                    if (expandableNotificationRow != null) {
                        expandableNotificationRow.updateBubbleButton();
                        break;
                    }
                }
                break;
        }
    }
}
