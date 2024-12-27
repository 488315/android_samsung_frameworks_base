package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.FeedbackIcon;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.collection.render.NotifRowController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationGroupHeaderViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

@CoordinatorScope
public final class RowAppearanceCoordinator implements Coordinator {
    public static final int $stable = 8;
    private NotificationEntry entryToExpand;
    private final boolean mAlwaysExpandNonGroupedNotification;
    private AssistantFeedbackController mAssistantFeedbackController;
    private final boolean mAutoExpandFirstNotification;
    private SectionStyleProvider mSectionStyleProvider;

    public RowAppearanceCoordinator(Context context, AssistantFeedbackController assistantFeedbackController, SectionStyleProvider sectionStyleProvider) {
        this.mAssistantFeedbackController = assistantFeedbackController;
        this.mSectionStyleProvider = sectionStyleProvider;
        this.mAlwaysExpandNonGroupedNotification = context.getResources().getBoolean(R.bool.config_alwaysExpandNonGroupedNotifications);
        this.mAutoExpandFirstNotification = context.getResources().getBoolean(R.bool.config_autoExpandFirstNotification);
    }

    public final void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController) {
        boolean z = notificationEntry.isOngoingAcitivty() && notificationEntry.isPromotedState();
        ExpandableNotificationRowController expandableNotificationRowController = (ExpandableNotificationRowController) notifRowController;
        ExpandableNotificationRow expandableNotificationRow = expandableNotificationRowController.mView;
        if (z != expandableNotificationRow.mIsSystemExpanded) {
            boolean isExpanded = expandableNotificationRow.isExpanded(false);
            expandableNotificationRow.mIsSystemExpanded = z;
            expandableNotificationRow.notifyHeightChanged(false);
            expandableNotificationRow.onExpansionChanged(false, isExpanded);
            if (expandableNotificationRow.mIsSummaryWithChildren) {
                expandableNotificationRow.mChildrenContainer.updateGroupOverflow();
                if (expandableNotificationRow.mIsSummaryWithChildren) {
                    expandableNotificationRow.mChildrenContainer.getClass();
                }
            }
        }
        AssistantFeedbackController assistantFeedbackController = this.mAssistantFeedbackController;
        FeedbackIcon feedbackIcon = (FeedbackIcon) assistantFeedbackController.mIcons.get(assistantFeedbackController.getFeedbackStatus(notificationEntry));
        ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRowController.mView;
        if (expandableNotificationRow2.mIsSummaryWithChildren) {
            NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow2.mChildrenContainer;
            NotificationHeaderViewWrapper notificationHeaderViewWrapper = notificationChildrenContainer.mGroupHeaderWrapper;
            if (notificationHeaderViewWrapper != null) {
                notificationHeaderViewWrapper.setFeedbackIcon(feedbackIcon);
            }
            NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = notificationChildrenContainer.mMinimizedGroupHeaderWrapper;
            if (notificationHeaderViewWrapper2 != null) {
                notificationHeaderViewWrapper2.setFeedbackIcon(feedbackIcon);
            }
            NotificationGroupHeaderViewWrapper notificationGroupHeaderViewWrapper = notificationChildrenContainer.mNotificationHeaderWrapperExpanded;
            if (notificationGroupHeaderViewWrapper != null) {
                notificationGroupHeaderViewWrapper.setFeedbackIcon(feedbackIcon);
            }
        }
        NotificationContentView notificationContentView = expandableNotificationRow2.mPrivateLayout;
        if (notificationContentView.mContractedChild != null) {
            notificationContentView.mContractedWrapper.setFeedbackIcon(feedbackIcon);
        }
        if (notificationContentView.mExpandedChild != null) {
            notificationContentView.mExpandedWrapper.setFeedbackIcon(feedbackIcon);
        }
        if (notificationContentView.mHeadsUpChild != null) {
            notificationContentView.mHeadsUpWrapper.setFeedbackIcon(feedbackIcon);
        }
        NotificationContentView notificationContentView2 = expandableNotificationRow2.mPublicLayout;
        if (notificationContentView2.mContractedChild != null) {
            notificationContentView2.mContractedWrapper.setFeedbackIcon(feedbackIcon);
        }
        if (notificationContentView2.mExpandedChild != null) {
            notificationContentView2.mExpandedWrapper.setFeedbackIcon(feedbackIcon);
        }
        if (notificationContentView2.mHeadsUpChild != null) {
            notificationContentView2.mHeadsUpWrapper.setFeedbackIcon(feedbackIcon);
        }
    }

    public final void onBeforeRenderList(List<? extends ListEntry> list) {
        NotificationEntry representativeEntry;
        ListEntry listEntry = (ListEntry) CollectionsKt___CollectionsKt.firstOrNull((List) list);
        NotificationEntry notificationEntry = null;
        if (listEntry != null && (representativeEntry = listEntry.getRepresentativeEntry()) != null) {
            SectionStyleProvider sectionStyleProvider = this.mSectionStyleProvider;
            Intrinsics.checkNotNull(representativeEntry.getSection());
            Set set = sectionStyleProvider.lowPrioritySections;
            if (set == null) {
                set = null;
            }
            if (!set.contains(r2.sectioner)) {
                notificationEntry = representativeEntry;
            }
        }
        this.entryToExpand = notificationEntry;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAppearanceCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List<? extends ListEntry> list) {
                RowAppearanceCoordinator.this.onBeforeRenderList(list);
            }
        });
        ((ArrayList) notifPipeline.mRenderStageManager.onAfterRenderEntryListeners).add(new OnAfterRenderEntryListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAppearanceCoordinator$attach$2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener
            public final void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController) {
                RowAppearanceCoordinator.this.onAfterRenderEntry(notificationEntry, notifRowController);
            }
        });
    }
}
