package com.android.systemui.statusbar.notification.collection.coordinator;

import android.R;
import android.view.NotificationHeaderView;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;

public final class NotifHeaderCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final NotificationLockscreenUserManager lockscreenUserManager;

    public NotifHeaderCoordinator(NotificationLockscreenUserManager notificationLockscreenUserManager) {
        this.lockscreenUserManager = notificationLockscreenUserManager;
    }

    public final Sequence extractAllRepresentativeEntries(List<? extends ListEntry> list) {
        return SequencesKt___SequencesKt.flatMap(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new NotifHeaderCoordinator$extractAllRepresentativeEntries$1(this));
    }

    public final void onBeforeRenderListListener(List<? extends ListEntry> list) {
        NotificationHeaderView notificationHeaderView;
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(extractAllRepresentativeEntries(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifHeaderCoordinator$onBeforeRenderListListener$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(NotificationEntry notificationEntry) {
                return Boolean.valueOf(notificationEntry.rowExists());
            }
        }));
        while (filteringSequence$iterator$1.hasNext()) {
            NotificationEntry notificationEntry = (NotificationEntry) filteringSequence$iterator$1.next();
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
            boolean isLockscreenPublicMode = notificationLockscreenUserManagerImpl.isLockscreenPublicMode(notificationLockscreenUserManagerImpl.mCurrentUserId);
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            if (expandableNotificationRow.mIsGroupHeaderContainAtMark) {
                NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                TextView textView = (notificationChildrenContainer == null || (notificationHeaderView = notificationChildrenContainer.mGroupHeader) == null) ? null : (TextView) notificationHeaderView.findViewById(R.id.inbox_text0);
                if (textView != null) {
                    textView.setVisibility(isLockscreenPublicMode ? 8 : 0);
                }
            }
            NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
            for (NotificationContentView notificationContentView : (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length)) {
                if (notificationContentView.mIsContractedHeaderContainAtMark) {
                    View view = notificationContentView.mContractedChild;
                    TextView textView2 = view != null ? (TextView) view.findViewById(R.id.inbox_text0) : null;
                    if (textView2 != null) {
                        textView2.setVisibility(isLockscreenPublicMode ? 8 : 0);
                    }
                }
                if (notificationContentView.mIsExpandedHeaderContainAtMark) {
                    View view2 = notificationContentView.mExpandedChild;
                    TextView textView3 = view2 != null ? (TextView) view2.findViewById(R.id.inbox_text0) : null;
                    if (textView3 != null) {
                        textView3.setVisibility(isLockscreenPublicMode ? 8 : 0);
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifHeaderCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List<? extends ListEntry> list) {
                NotifHeaderCoordinator.this.onBeforeRenderListListener(list);
            }
        });
    }

    public final Sequence extractAllRepresentativeEntries(ListEntry listEntry) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new NotifHeaderCoordinator$extractAllRepresentativeEntries$2(listEntry, this, null));
    }
}
