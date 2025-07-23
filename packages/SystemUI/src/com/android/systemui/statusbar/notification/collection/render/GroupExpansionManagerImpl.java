package com.android.systemui.statusbar.notification.collection.render;

import android.util.Log;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.EntryAdapter;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda3;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class GroupExpansionManagerImpl implements GroupExpansionManager, Dumpable {
    public final DumpManager mDumpManager;
    public final GroupMembershipManager mGroupMembershipManager;
    public final Set mOnGroupChangeListeners = new HashSet();
    public final Set mExpandedGroups = new HashSet();
    public final Set mExpandedCollections = new HashSet();
    public final GroupExpansionManagerImpl$$ExternalSyntheticLambda0 mNotifTracker = new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl$$ExternalSyntheticLambda0
        /* JADX WARN: Removed duplicated region for block: B:24:0x007d A[LOOP:1: B:22:0x0077->B:24:0x007d, LOOP_END] */
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onBeforeRenderList(java.util.List r5) {
            /*
                r4 = this;
                com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl r4 = com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl.this
                int r0 = com.android.systemui.statusbar.notification.shared.NotificationBundleUi.$r8$clinit
                java.util.Set r0 = r4.mExpandedGroups
                java.util.HashSet r0 = (java.util.HashSet) r0
                boolean r0 = r0.isEmpty()
                if (r0 == 0) goto L10
                goto L88
            L10:
                java.util.HashSet r0 = new java.util.HashSet
                r0.<init>()
                java.util.Iterator r5 = r5.iterator()
            L19:
                boolean r1 = r5.hasNext()
                if (r1 == 0) goto L31
                java.lang.Object r1 = r5.next()
                com.android.systemui.statusbar.notification.collection.PipelineEntry r1 = (com.android.systemui.statusbar.notification.collection.PipelineEntry) r1
                boolean r2 = r1 instanceof com.android.systemui.statusbar.notification.collection.GroupEntry
                if (r2 == 0) goto L19
                com.android.systemui.statusbar.notification.collection.GroupEntry r1 = (com.android.systemui.statusbar.notification.collection.GroupEntry) r1
                com.android.systemui.statusbar.notification.collection.NotificationEntry r1 = r1.mSummary
                r0.add(r1)
                goto L19
            L31:
                int r5 = com.android.systemui.statusbar.notification.shared.NotificationBundleUi.$r8$clinit
                java.util.Set r5 = r4.mExpandedGroups
                if (r5 == 0) goto L6e
                r1 = r5
                java.util.HashSet r1 = (java.util.HashSet) r1
                boolean r2 = r1.isEmpty()
                if (r2 == 0) goto L41
                goto L6e
            L41:
                boolean r2 = r0.isEmpty()
                if (r2 == 0) goto L4d
                java.util.HashSet r0 = new java.util.HashSet
                r0.<init>(r5)
                goto L73
            L4d:
                java.util.HashSet r5 = new java.util.HashSet
                r5.<init>()
                java.util.Iterator r1 = r1.iterator()
            L56:
                boolean r2 = r1.hasNext()
                if (r2 == 0) goto L6c
                java.lang.Object r2 = r1.next()
                com.android.systemui.statusbar.notification.collection.NotificationEntry r2 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r2
                boolean r3 = r0.contains(r2)
                if (r3 != 0) goto L56
                r5.add(r2)
                goto L56
            L6c:
                r0 = r5
                goto L73
            L6e:
                java.util.HashSet r0 = new java.util.HashSet
                r0.<init>()
            L73:
                java.util.Iterator r5 = r0.iterator()
            L77:
                boolean r0 = r5.hasNext()
                if (r0 == 0) goto L88
                java.lang.Object r0 = r5.next()
                com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r0
                r1 = 0
                r4.setGroupExpanded(r0, r1)
                goto L77
            L88:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl$$ExternalSyntheticLambda0.onBeforeRenderList(java.util.List):void");
        }
    };

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl$$ExternalSyntheticLambda0] */
    public GroupExpansionManagerImpl(DumpManager dumpManager, GroupMembershipManager groupMembershipManager) {
        this.mDumpManager = dumpManager;
        this.mGroupMembershipManager = groupMembershipManager;
    }

    public final void collapseGroups() {
        int i = NotificationBundleUi.$r8$clinit;
        ArrayList arrayList = new ArrayList(this.mExpandedGroups);
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            setGroupExpanded((NotificationEntry) obj, false);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "NotificationEntryExpansion state:", "  mExpandedGroups: ");
        m.append(((HashSet) this.mExpandedGroups).size());
        printWriter.println(m.toString());
        Iterator it = ((HashSet) this.mExpandedGroups).iterator();
        while (it.hasNext()) {
            NotificationEntry notificationEntry = (NotificationEntry) it.next();
            if (notificationEntry != null) {
                CarrierTextController$$ExternalSyntheticOutline0.m(new StringBuilder("  * "), notificationEntry.mKey, printWriter);
            }
        }
        printWriter.println("  mExpandedCollection: " + ((HashSet) this.mExpandedCollections).size());
        Iterator it2 = ((HashSet) this.mExpandedCollections).iterator();
        while (it2.hasNext()) {
            printWriter.println("  * " + ((EntryAdapter) it2.next()).getKey());
        }
    }

    public final boolean isGroupExpanded(NotificationEntry notificationEntry) {
        int i = NotificationBundleUi.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        return ((HashSet) this.mExpandedGroups).contains(((GroupMembershipManagerImpl) this.mGroupMembershipManager).getGroupSummary(notificationEntry));
    }

    public final void setGroupExpanded(NotificationEntry notificationEntry, boolean z) {
        boolean remove;
        ExpandableNotificationRow expandableNotificationRow;
        NotificationChildrenContainer notificationChildrenContainer;
        int i = NotificationBundleUi.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (notificationEntry == null) {
            return;
        }
        NotificationEntry groupSummary = ((GroupMembershipManagerImpl) this.mGroupMembershipManager).getGroupSummary(notificationEntry);
        if (notificationEntry.mAttachState.parent == null) {
            if (z) {
                Log.wtf("GroupExpansionaManagerImpl", "Cannot expand group that is not attached");
            } else {
                groupSummary = notificationEntry;
            }
        }
        if (z) {
            remove = ((HashSet) this.mExpandedGroups).add(groupSummary);
        } else {
            remove = ((HashSet) this.mExpandedGroups).remove(groupSummary);
            ((HashSet) this.mExpandedGroups).remove(notificationEntry);
        }
        if (notificationEntry.rowIsChildInGroup() && groupSummary != null && (expandableNotificationRow = groupSummary.row) != null && (notificationChildrenContainer = expandableNotificationRow.mChildrenContainer) != null) {
            notificationChildrenContainer.setChildrenExpanded(z);
            notificationChildrenContainer.updateHeaderForExpansion(z);
        }
        if (remove) {
            Iterator it = ((HashSet) this.mOnGroupChangeListeners).iterator();
            while (it.hasNext()) {
                NotificationStackScrollLayoutController$$ExternalSyntheticLambda3 notificationStackScrollLayoutController$$ExternalSyntheticLambda3 = (NotificationStackScrollLayoutController$$ExternalSyntheticLambda3) it.next();
                ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController$$ExternalSyntheticLambda3.f$0.mView;
                if (notificationStackScrollLayout.mAnimationsEnabled && ((notificationStackScrollLayout.mIsExpanded || expandableNotificationRow2.mPinnedStatus.isPinned()) && !notificationStackScrollLayout.onKeyguard())) {
                    notificationStackScrollLayout.mExpandedGroupView = expandableNotificationRow2;
                    notificationStackScrollLayout.mNeedsAnimation = true;
                }
                expandableNotificationRow2.mChildrenExpanded = z;
                NotificationChildrenContainer notificationChildrenContainer2 = expandableNotificationRow2.mChildrenContainer;
                if (notificationChildrenContainer2 != null) {
                    notificationChildrenContainer2.setChildrenExpanded(z);
                }
                expandableNotificationRow2.updateBackgroundForGroupState();
                expandableNotificationRow2.updateClickAndFocus();
                notificationStackScrollLayout.onChildHeightChanged(expandableNotificationRow2, false);
                notificationStackScrollLayout.mAnimationFinishedRunnables.add(new NotificationStackScrollLayout$$ExternalSyntheticLambda0(expandableNotificationRow2));
            }
        }
    }
}
