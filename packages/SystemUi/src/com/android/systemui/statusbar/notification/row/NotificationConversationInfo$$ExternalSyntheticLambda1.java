package com.android.systemui.statusbar.notification.row;

import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl;
import com.android.systemui.statusbar.notification.row.NotificationConversationInfo;
import com.android.systemui.wmshell.BubblesManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationConversationInfo$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationConversationInfo$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationConversationInfo notificationConversationInfo = (NotificationConversationInfo) this.f$0;
                ((OnUserInteractionCallbackImpl) notificationConversationInfo.mOnUserInteractionCallback).onImportanceChanged(notificationConversationInfo.mEntry);
                break;
            default:
                NotificationConversationInfo.UpdateChannelRunnable updateChannelRunnable = (NotificationConversationInfo.UpdateChannelRunnable) this.f$0;
                BubblesManager bubblesManager = (BubblesManager) NotificationConversationInfo.this.mBubblesManagerOptional.get();
                NotificationEntry notificationEntry = NotificationConversationInfo.this.mEntry;
                bubblesManager.getClass();
                if (notificationEntry.mBubbleMetadata != null) {
                    try {
                        bubblesManager.mBarService.onNotificationBubbleChanged(notificationEntry.mKey, true, 2);
                    } catch (RemoteException e) {
                        Log.e("BubblesManager", e.getMessage());
                    }
                    ((ShadeControllerImpl) bubblesManager.mShadeController).collapseShade(true);
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
