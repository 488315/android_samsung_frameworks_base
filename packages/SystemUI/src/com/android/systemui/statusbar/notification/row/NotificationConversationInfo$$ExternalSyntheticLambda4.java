package com.android.systemui.statusbar.notification.row;

import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.UseElapsedRealtimeForCreationTime;
import com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl;
import com.android.systemui.statusbar.notification.row.NotificationConversationInfo;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.wmshell.BubblesManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
                NotificationEntry notificationEntry = notificationConversationInfo.mEntry;
                OnUserInteractionCallbackImpl onUserInteractionCallbackImpl = (OnUserInteractionCallbackImpl) onUserInteractionCallback;
                onUserInteractionCallbackImpl.getClass();
                int i2 = NotificationBundleUi.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i3 = UseElapsedRealtimeForCreationTime.$r8$clinit;
                onUserInteractionCallbackImpl.mVisualStabilityCoordinator.temporarilyAllowSectionChanges(notificationEntry, SystemClock.uptimeMillis());
                break;
            default:
                NotificationConversationInfo.UpdateChannelRunnable updateChannelRunnable = (NotificationConversationInfo.UpdateChannelRunnable) obj;
                BubblesManager bubblesManager = (BubblesManager) NotificationConversationInfo.this.mBubblesManagerOptional.get();
                NotificationEntry notificationEntry2 = NotificationConversationInfo.this.mEntry;
                bubblesManager.getClass();
                if (notificationEntry2.mBubbleMetadata != null) {
                    try {
                        bubblesManager.mBarService.onNotificationBubbleChanged(notificationEntry2.mKey, true, 2);
                    } catch (RemoteException e) {
                        Log.e("Bubbles", e.getMessage());
                    }
                    bubblesManager.mShadeController.collapseShade(true);
                    ExpandableNotificationRow expandableNotificationRow = notificationEntry2.row;
                    if (expandableNotificationRow != null) {
                        expandableNotificationRow.updateBubbleButton();
                        break;
                    }
                }
                break;
        }
    }
}
