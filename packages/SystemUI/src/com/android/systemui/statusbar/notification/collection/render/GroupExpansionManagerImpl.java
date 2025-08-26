package com.android.systemui.statusbar.notification.collection.render;

import android.util.Log;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.EntryAdapter;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
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
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public class GroupExpansionManagerImpl implements GroupExpansionManager, Dumpable {
    public final DumpManager mDumpManager;
    public final GroupMembershipManager mGroupMembershipManager;
    public final Set mOnGroupChangeListeners = new HashSet();
    public final Set mExpandedGroups = new HashSet();
    public final Set mExpandedCollections = new HashSet();
    public final GroupExpansionManagerImpl$$ExternalSyntheticLambda0 mNotifTracker = new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl$$ExternalSyntheticLambda0
        /* JADX WARN: Removed duplicated region for block: B:26:0x006e  */
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onBeforeRenderList(List list) {
            HashSet hashSet;
            GroupExpansionManagerImpl groupExpansionManagerImpl = this.f$0;
            int i = NotificationBundleUi.$r8$clinit;
            if (((HashSet) groupExpansionManagerImpl.mExpandedGroups).isEmpty()) {
                return;
            }
            HashSet hashSet2 = new HashSet();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                PipelineEntry pipelineEntry = (PipelineEntry) it.next();
                if (pipelineEntry instanceof GroupEntry) {
                    hashSet2.add(((GroupEntry) pipelineEntry).mSummary);
                }
            }
            int i2 = NotificationBundleUi.$r8$clinit;
            Set set = groupExpansionManagerImpl.mExpandedGroups;
            if (set != null) {
                HashSet hashSet3 = (HashSet) set;
                if (hashSet3.isEmpty()) {
                    hashSet = new HashSet();
                } else if (hashSet2.isEmpty()) {
                    hashSet = new HashSet(set);
                } else {
                    HashSet hashSet4 = new HashSet();
                    Iterator it2 = hashSet3.iterator();
                    while (it2.hasNext()) {
                        NotificationEntry notificationEntry = (NotificationEntry) it2.next();
                        if (!hashSet2.contains(notificationEntry)) {
                            hashSet4.add(notificationEntry);
                        }
                    }
                    hashSet = hashSet4;
                }
            }
            Iterator it3 = hashSet.iterator();
            while (it3.hasNext()) {
                groupExpansionManagerImpl.setGroupExpanded((NotificationEntry) it3.next(), false);
            }
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
        StringBuilder sbM = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "NotificationEntryExpansion state:", "  mExpandedGroups: ");
        sbM.append(((HashSet) this.mExpandedGroups).size());
        printWriter.println(sbM.toString());
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
        boolean zRemove;
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
            zRemove = ((HashSet) this.mExpandedGroups).add(groupSummary);
        } else {
            zRemove = ((HashSet) this.mExpandedGroups).remove(groupSummary);
            ((HashSet) this.mExpandedGroups).remove(notificationEntry);
        }
        if (notificationEntry.rowIsChildInGroup() && groupSummary != null && (expandableNotificationRow = groupSummary.row) != null && (notificationChildrenContainer = expandableNotificationRow.mChildrenContainer) != null) {
            notificationChildrenContainer.setChildrenExpanded(z);
            notificationChildrenContainer.updateHeaderForExpansion(z);
        }
        if (zRemove) {
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
