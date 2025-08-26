package com.android.systemui.wmshell;

import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.wmshell.BubblesManager;
import com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda4;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubblesManager$5$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubblesManager.AnonymousClass5 f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BubblesManager$5$$ExternalSyntheticLambda1(BubblesManager.AnonymousClass5 anonymousClass5, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass5;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ExpandableNotificationRow expandableNotificationRow;
        switch (this.$r8$classId) {
            case 0:
                NotificationEntry entry = ((NotifPipeline) BubblesManager.this.mCommonNotifCollection).mNotifCollection.getEntry((String) this.f$1);
                if (entry != null && (expandableNotificationRow = entry.row) != null) {
                    expandableNotificationRow.updateBubbleButton();
                    break;
                }
                break;
            case 1:
                NotificationEntry entry2 = ((NotifPipeline) BubblesManager.this.mCommonNotifCollection).mNotifCollection.getEntry((String) this.f$1);
                if (entry2 != null && entry2.mRanking.getImportance() >= 4) {
                    entry2.interruption = true;
                    break;
                }
                break;
            case 2:
                BubblesManager.AnonymousClass5 anonymousClass5 = this.f$0;
                String str = (String) this.f$1;
                BubblesManager bubblesManager = BubblesManager.this;
                NotificationEntry entry3 = ((NotifPipeline) bubblesManager.mCommonNotifCollection).mNotifCollection.getEntry(str);
                if (entry3 != null) {
                    ArrayList arrayList = (ArrayList) bubblesManager.mCallbacks;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((BubblesManager.NotifCallback) obj).removeNotification(entry3, new DismissedByUserStats(4, 1, ((NotificationVisibilityProviderImpl) bubblesManager.mVisibilityProvider).obtain(entry3)), 2);
                    }
                    break;
                }
                break;
            case 3:
                BubblesManager.AnonymousClass5 anonymousClass52 = this.f$0;
                String str2 = (String) this.f$1;
                ArrayList arrayList2 = (ArrayList) BubblesManager.this.mCallbacks;
                int size2 = arrayList2.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    ((BubblesManager.NotifCallback) obj2).invalidateNotifications(str2);
                }
                break;
            default:
                ((BubbleController$$ExternalSyntheticLambda4) this.f$1).accept(Boolean.valueOf(((NotificationShadeWindowControllerImpl) BubblesManager.this.mNotificationShadeWindowController).mCurrentState.shadeOrQsExpanded));
                break;
        }
    }
}
