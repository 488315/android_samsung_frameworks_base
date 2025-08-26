package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import java.util.List;

/* loaded from: classes3.dex */
public class GroupMembershipManagerImpl implements GroupMembershipManager {
    public final List getChildren(PipelineEntry pipelineEntry) {
        int i = NotificationBundleUi.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (pipelineEntry instanceof GroupEntry) {
            return ((GroupEntry) pipelineEntry).mUnmodifiableChildren;
        }
        NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
        if (representativeEntry == null || !isGroupSummary(representativeEntry)) {
            return null;
        }
        PipelineEntry pipelineEntry2 = representativeEntry.mAttachState.parent;
        if (pipelineEntry2 instanceof GroupEntry) {
            return ((GroupEntry) pipelineEntry2).mUnmodifiableChildren;
        }
        return null;
    }

    public final NotificationEntry getGroupSummary(NotificationEntry notificationEntry) {
        PipelineEntry pipelineEntry;
        int i = NotificationBundleUi.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (notificationEntry == null || (pipelineEntry = notificationEntry.mAttachState.parent) == GroupEntry.ROOT_ENTRY || pipelineEntry == null || !(pipelineEntry instanceof GroupEntry)) {
            return null;
        }
        return ((GroupEntry) pipelineEntry).mSummary;
    }

    public final boolean isGroupSummary(NotificationEntry notificationEntry) {
        int i = NotificationBundleUi.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        PipelineEntry pipelineEntry = notificationEntry.mAttachState.parent;
        return pipelineEntry != null && (pipelineEntry instanceof GroupEntry) && ((GroupEntry) pipelineEntry).mSummary == notificationEntry;
    }
}
