package com.android.systemui.statusbar.notification.collection.render;

import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.C2946xbae1b0c4;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GroupExpansionManagerImpl implements GroupExpansionManager, Dumpable {
    public final GroupMembershipManager mGroupMembershipManager;
    public final Set mOnGroupChangeListeners = new HashSet();
    public final Set mExpandedGroups = new HashSet();

    public GroupExpansionManagerImpl(DumpManager dumpManager, GroupMembershipManager groupMembershipManager) {
        this.mGroupMembershipManager = groupMembershipManager;
    }

    public final void collapseGroups() {
        Iterator it = new ArrayList(this.mExpandedGroups).iterator();
        while (it.hasNext()) {
            setGroupExpanded((NotificationEntry) it.next(), false);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "NotificationEntryExpansion state:", "  mExpandedGroups: ");
        Set set = this.mExpandedGroups;
        m75m.append(((HashSet) set).size());
        printWriter.println(m75m.toString());
        Iterator it = ((HashSet) set).iterator();
        while (it.hasNext()) {
            KeyboardUI$$ExternalSyntheticOutline0.m134m(new StringBuilder("  * "), ((NotificationEntry) it.next()).mKey, printWriter);
        }
    }

    public final boolean isGroupExpanded(NotificationEntry notificationEntry) {
        return ((HashSet) this.mExpandedGroups).contains(((GroupMembershipManagerImpl) this.mGroupMembershipManager).getGroupSummary(notificationEntry));
    }

    public final void setGroupExpanded(NotificationEntry notificationEntry, boolean z) {
        ExpandableNotificationRow expandableNotificationRow;
        NotificationChildrenContainer notificationChildrenContainer;
        if (notificationEntry == null) {
            return;
        }
        NotificationEntry groupSummary = ((GroupMembershipManagerImpl) this.mGroupMembershipManager).getGroupSummary(notificationEntry);
        Set set = this.mExpandedGroups;
        if (z) {
            ((HashSet) set).add(groupSummary);
        } else {
            HashSet hashSet = (HashSet) set;
            hashSet.remove(groupSummary);
            hashSet.remove(notificationEntry);
        }
        if (notificationEntry.isChildInGroup() && groupSummary != null && (expandableNotificationRow = groupSummary.row) != null && (notificationChildrenContainer = expandableNotificationRow.mChildrenContainer) != null) {
            notificationChildrenContainer.setChildrenExpanded$1(z);
            notificationChildrenContainer.updateHeaderForExpansion(z);
        }
        Iterator it = ((HashSet) this.mOnGroupChangeListeners).iterator();
        while (it.hasNext()) {
            C2946xbae1b0c4 c2946xbae1b0c4 = (C2946xbae1b0c4) it.next();
            ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
            NotificationStackScrollLayout notificationStackScrollLayout = c2946xbae1b0c4.f$0.mView;
            if (notificationStackScrollLayout.mAnimationsEnabled && (notificationStackScrollLayout.mIsExpanded || expandableNotificationRow2.mIsPinned)) {
                notificationStackScrollLayout.mExpandedGroupView = expandableNotificationRow2;
                notificationStackScrollLayout.mNeedsAnimation = true;
            }
            expandableNotificationRow2.setChildrenExpanded(z);
            notificationStackScrollLayout.onChildHeightChanged(expandableNotificationRow2, false);
            notificationStackScrollLayout.mAnimationFinishedRunnables.add(new Runnable(notificationStackScrollLayout, expandableNotificationRow2) { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.16
                public final /* synthetic */ ExpandableNotificationRow val$changedRow;

                {
                    this.val$changedRow = expandableNotificationRow2;
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
