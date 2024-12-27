package com.android.systemui.wmshell;

import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.wmshell.BubblesManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                if (entry != null && entry.mRanking.getImportance() >= 4) {
                    entry.interruption = true;
                    break;
                }
                break;
            case 1:
                BubblesManager.AnonymousClass5 anonymousClass5 = this.f$0;
                String str = (String) this.f$1;
                Iterator it = ((ArrayList) BubblesManager.this.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((BubblesManager.NotifCallback) it.next()).invalidateNotifications(str);
                }
                break;
            case 2:
                NotificationEntry entry2 = ((NotifPipeline) BubblesManager.this.mCommonNotifCollection).mNotifCollection.getEntry((String) this.f$1);
                if (entry2 != null && (expandableNotificationRow = entry2.row) != null) {
                    expandableNotificationRow.updateBubbleButton();
                    break;
                }
                break;
            case 3:
                BubblesManager.AnonymousClass5 anonymousClass52 = this.f$0;
                String str2 = (String) this.f$1;
                BubblesManager bubblesManager = BubblesManager.this;
                NotificationEntry entry3 = ((NotifPipeline) bubblesManager.mCommonNotifCollection).mNotifCollection.getEntry(str2);
                if (entry3 != null) {
                    bubblesManager.onUserChangedBubble(entry3, false);
                    break;
                }
                break;
            default:
                ((Consumer) this.f$1).accept(Boolean.valueOf(((NotificationShadeWindowControllerImpl) BubblesManager.this.mNotificationShadeWindowController).mCurrentState.shadeOrQsExpanded));
                break;
        }
    }
}
