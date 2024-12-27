package com.android.systemui.statusbar.notification.collection.render;

import android.util.Log;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda6;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class GroupExpansionManagerImpl implements GroupExpansionManager, Dumpable {
    public final DumpManager mDumpManager;
    public final GroupMembershipManager mGroupMembershipManager;
    public final Set mOnGroupChangeListeners = new HashSet();
    public final Set mExpandedGroups = new HashSet();
    public final GroupExpansionManagerImpl$$ExternalSyntheticLambda0 mNotifTracker = new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl$$ExternalSyntheticLambda0
        /* JADX WARN: Removed duplicated region for block: B:24:0x0079 A[LOOP:1: B:22:0x0073->B:24:0x0079, LOOP_END] */
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onBeforeRenderList(java.util.List r5) {
            /*
                r4 = this;
                com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl r4 = com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl.this
                java.util.Set r0 = r4.mExpandedGroups
                java.util.HashSet r0 = (java.util.HashSet) r0
                boolean r0 = r0.isEmpty()
                if (r0 == 0) goto Le
                goto L84
            Le:
                java.util.HashSet r0 = new java.util.HashSet
                r0.<init>()
                java.util.Iterator r5 = r5.iterator()
            L17:
                boolean r1 = r5.hasNext()
                if (r1 == 0) goto L2f
                java.lang.Object r1 = r5.next()
                com.android.systemui.statusbar.notification.collection.ListEntry r1 = (com.android.systemui.statusbar.notification.collection.ListEntry) r1
                boolean r2 = r1 instanceof com.android.systemui.statusbar.notification.collection.GroupEntry
                if (r2 == 0) goto L17
                com.android.systemui.statusbar.notification.collection.GroupEntry r1 = (com.android.systemui.statusbar.notification.collection.GroupEntry) r1
                com.android.systemui.statusbar.notification.collection.NotificationEntry r1 = r1.mSummary
                r0.add(r1)
                goto L17
            L2f:
                java.util.Set r5 = r4.mExpandedGroups
                if (r5 == 0) goto L6a
                r1 = r5
                java.util.HashSet r1 = (java.util.HashSet) r1
                boolean r2 = r1.isEmpty()
                if (r2 == 0) goto L3d
                goto L6a
            L3d:
                boolean r2 = r0.isEmpty()
                if (r2 == 0) goto L49
                java.util.HashSet r0 = new java.util.HashSet
                r0.<init>(r5)
                goto L6f
            L49:
                java.util.HashSet r5 = new java.util.HashSet
                r5.<init>()
                java.util.Iterator r1 = r1.iterator()
            L52:
                boolean r2 = r1.hasNext()
                if (r2 == 0) goto L68
                java.lang.Object r2 = r1.next()
                com.android.systemui.statusbar.notification.collection.NotificationEntry r2 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r2
                boolean r3 = r0.contains(r2)
                if (r3 != 0) goto L52
                r5.add(r2)
                goto L52
            L68:
                r0 = r5
                goto L6f
            L6a:
                java.util.HashSet r0 = new java.util.HashSet
                r0.<init>()
            L6f:
                java.util.Iterator r5 = r0.iterator()
            L73:
                boolean r0 = r5.hasNext()
                if (r0 == 0) goto L84
                java.lang.Object r0 = r5.next()
                com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r0
                r1 = 0
                r4.setGroupExpanded(r0, r1)
                goto L73
            L84:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl$$ExternalSyntheticLambda0.onBeforeRenderList(java.util.List):void");
        }
    };

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl$$ExternalSyntheticLambda0] */
    public GroupExpansionManagerImpl(DumpManager dumpManager, GroupMembershipManager groupMembershipManager) {
        this.mDumpManager = dumpManager;
        this.mGroupMembershipManager = groupMembershipManager;
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
    }

    public final boolean isGroupExpanded(NotificationEntry notificationEntry) {
        return ((HashSet) this.mExpandedGroups).contains(((GroupMembershipManagerImpl) this.mGroupMembershipManager).getGroupSummary(notificationEntry));
    }

    public final void setGroupExpanded(NotificationEntry notificationEntry, boolean z) {
        boolean remove;
        ExpandableNotificationRow expandableNotificationRow;
        NotificationChildrenContainer notificationChildrenContainer;
        if (notificationEntry == null) {
            return;
        }
        NotificationEntry groupSummary = ((GroupMembershipManagerImpl) this.mGroupMembershipManager).getGroupSummary(notificationEntry);
        if (notificationEntry.getParent() == null) {
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
            notificationChildrenContainer.setChildrenExpanded$1(z);
            notificationChildrenContainer.updateHeaderForExpansion(z);
        }
        if (remove) {
            Iterator it = ((HashSet) this.mOnGroupChangeListeners).iterator();
            while (it.hasNext()) {
                NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 notificationStackScrollLayoutController$$ExternalSyntheticLambda6 = (NotificationStackScrollLayoutController$$ExternalSyntheticLambda6) it.next();
                ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController$$ExternalSyntheticLambda6.f$0.mView;
                if (notificationStackScrollLayout.mAnimationsEnabled && (notificationStackScrollLayout.mIsExpanded || expandableNotificationRow2.mIsPinned)) {
                    notificationStackScrollLayout.mExpandedGroupView = expandableNotificationRow2;
                    notificationStackScrollLayout.mNeedsAnimation = true;
                }
                expandableNotificationRow2.mChildrenExpanded = z;
                NotificationChildrenContainer notificationChildrenContainer2 = expandableNotificationRow2.mChildrenContainer;
                if (notificationChildrenContainer2 != null) {
                    notificationChildrenContainer2.setChildrenExpanded$1(z);
                }
                expandableNotificationRow2.updateBackgroundForGroupState();
                expandableNotificationRow2.updateClickAndFocus();
                notificationStackScrollLayout.onChildHeightChanged(expandableNotificationRow2, false);
                notificationStackScrollLayout.mAnimationFinishedRunnables.add(new Runnable(notificationStackScrollLayout, expandableNotificationRow2) { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.14
                    public final /* synthetic */ ExpandableNotificationRow val$changedRow;

                    public AnonymousClass14(NotificationStackScrollLayout notificationStackScrollLayout2, ExpandableNotificationRow expandableNotificationRow22) {
                        this.val$changedRow = expandableNotificationRow22;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        ExpandableNotificationRow expandableNotificationRow3 = this.val$changedRow;
                        expandableNotificationRow3.mGroupExpansionChanging = false;
                        expandableNotificationRow3.updateBackgroundForGroupState();
                    }
                });
            }
        }
    }
}
