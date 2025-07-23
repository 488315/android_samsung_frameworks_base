package com.android.systemui.statusbar.phone;

import android.app.NotificationChannel;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.statusbar.notification.NotificationChannelHelper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.wmshell.BubblesManager;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda4;
import com.android.wm.shell.bubbles.BubbleEntry;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StatusBarNotificationActivityStarter f$0;
    public final /* synthetic */ NotificationEntry f$1;

    public /* synthetic */ StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4(StatusBarNotificationActivityStarter statusBarNotificationActivityStarter, NotificationEntry notificationEntry, int i) {
        this.$r8$classId = i;
        this.f$0 = statusBarNotificationActivityStarter;
        this.f$1 = notificationEntry;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = this.f$0;
                final NotificationEntry notificationEntry = this.f$1;
                statusBarNotificationActivityStarter.mBubblesManagerOptional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NotificationEntry notificationEntry2 = NotificationEntry.this;
                        BubblesManager bubblesManager = (BubblesManager) obj;
                        boolean isBubble = notificationEntry2.isBubble();
                        boolean z = !isBubble;
                        bubblesManager.getClass();
                        NotificationChannel channel = notificationEntry2.mRanking.getChannel();
                        String packageName = notificationEntry2.mSbn.getPackageName();
                        int uid = notificationEntry2.mSbn.getUid();
                        if (channel == null || packageName == null) {
                            return;
                        }
                        notificationEntry2.isBubble();
                        if (isBubble) {
                            notificationEntry2.mSbn.getNotification().flags &= -4097;
                        } else if (notificationEntry2.mBubbleMetadata != null && notificationEntry2.mRanking.canBubble()) {
                            notificationEntry2.mSbn.getNotification().flags |= 4096;
                        }
                        notificationEntry2.isBubble();
                        try {
                            bubblesManager.mBarService.onNotificationBubbleChanged(notificationEntry2.mKey, z, 3);
                        } catch (RemoteException unused) {
                        }
                        NotificationChannel createConversationChannelIfNeeded = NotificationChannelHelper.createConversationChannelIfNeeded(bubblesManager.mContext, bubblesManager.mNotificationManager, notificationEntry2, channel);
                        createConversationChannelIfNeeded.setAllowBubbles(z);
                        try {
                            int bubblePreferenceForPackage = bubblesManager.mNotificationManager.getBubblePreferenceForPackage(packageName, uid);
                            if (!isBubble && bubblePreferenceForPackage == 0) {
                                bubblesManager.mNotificationManager.setBubblesAllowed(packageName, uid, 2);
                            }
                            bubblesManager.mNotificationManager.updateNotificationChannelForPackage(packageName, uid, createConversationChannelIfNeeded);
                        } catch (RemoteException e) {
                            Log.e("Bubbles", e.getMessage());
                        }
                        if (isBubble) {
                            return;
                        }
                        bubblesManager.mShadeController.collapseShade(true);
                        ExpandableNotificationRow expandableNotificationRow = notificationEntry2.row;
                        if (expandableNotificationRow != null) {
                            expandableNotificationRow.updateBubbleButton();
                        }
                    }
                });
                ((HeadsUpManagerImpl) statusBarNotificationActivityStarter.mHeadsUpManager).removeNotification(notificationEntry.mKey, "onNotificationBubbleIconClicked", true);
                break;
            default:
                StatusBarNotificationActivityStarter statusBarNotificationActivityStarter2 = this.f$0;
                NotificationEntry notificationEntry2 = this.f$1;
                BubblesManager bubblesManager = (BubblesManager) statusBarNotificationActivityStarter2.mBubblesManagerOptional.get();
                BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry2);
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda4(bubblesImpl, notifToBubbleEntry, 1));
                statusBarNotificationActivityStarter2.mShadeController.collapseShade();
                break;
        }
    }
}
