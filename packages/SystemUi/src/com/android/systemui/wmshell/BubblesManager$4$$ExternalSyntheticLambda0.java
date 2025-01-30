package com.android.systemui.wmshell;

import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.wmshell.BubblesManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubblesManager$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubblesManager.C37394 f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BubblesManager$4$$ExternalSyntheticLambda0(BubblesManager.C37394 c37394, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = c37394;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ExpandableNotificationRow expandableNotificationRow;
        switch (this.$r8$classId) {
            case 0:
                NotificationEntry entry = ((NotifPipeline) BubblesManager.this.mCommonNotifCollection).getEntry((String) this.f$1);
                if (entry != null && entry.getImportance() >= 4) {
                    entry.interruption = true;
                    break;
                }
                break;
            case 1:
                NotificationEntry entry2 = ((NotifPipeline) BubblesManager.this.mCommonNotifCollection).getEntry((String) this.f$1);
                if (entry2 != null && (expandableNotificationRow = entry2.row) != null) {
                    expandableNotificationRow.updateBubbleButton();
                    break;
                }
                break;
            case 2:
                BubblesManager.C37394 c37394 = this.f$0;
                String str = (String) this.f$1;
                Iterator it = ((ArrayList) BubblesManager.this.mCallbacks).iterator();
                while (it.hasNext()) {
                    BubbleCoordinator.this.mNotifFilter.invalidateList(str);
                }
                break;
            case 3:
                BubblesManager.C37394 c373942 = this.f$0;
                String str2 = (String) this.f$1;
                BubblesManager bubblesManager = BubblesManager.this;
                NotificationEntry entry3 = ((NotifPipeline) bubblesManager.mCommonNotifCollection).getEntry(str2);
                if (entry3 != null) {
                    bubblesManager.onUserChangedBubble(entry3, false);
                    break;
                }
                break;
            default:
                ((Consumer) this.f$1).accept(Boolean.valueOf(((NotificationShadeWindowControllerImpl) BubblesManager.this.mNotificationShadeWindowController).mCurrentState.panelExpanded));
                break;
        }
    }
}
